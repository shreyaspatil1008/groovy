package examples


import com.xlson.groovycsv.CsvParser
import org.grails.orm.hibernate.HibernateDatastore

class Main{
    static void main(String[] args){
        irisDemo()
	}

    static void irisDemo(){
        def csvIterator = CsvParser.parseCsv(new File('iris.csv').newReader())
        def iris
        use(CsvIteratorToIrisIterator){
            iris = csvIterator.toIris().toList()
        }

        //println iris
        iris.collect{it.name}.toSet().each{println it}

        // max of sl for Iris-setosa
       println iris.findAll{it.name == 'Iris-setosa'}.collect{it.sl}.max()
        // max of sl for each name
        iris.groupBy{it.name}.collectEntries{k,v -> [k,['max':v.collect{it.sl}.max()]]}.each{println it}


        //sqlite3
        def sql = groovy.sql.Sql.newInstance("jdbc:sqlite:iris.db","org.sqlite.JDBC")
        sql.execute """
                        create table if  not exists IRIS(sl double, sq double, pl double , pw double , name varchar(20))
                        """
        println sql
        iris.each{
            row->
                sql.execute('insert into IRIS values (?,?,?,?,?)',row.toList())
        }
        def rows = sql.rows("select name, max(sl) as max_sl from IRIS group by name")
        rows.collectEntries{m -> [m.name, m.max_sl]}.each{println it}
        sql.close()

        Map configuration = [
                'hibernate.hbm2ddl.auto':'create-drop',
                'dataSource.url':'jdbc:h2:mem:myDB',
                'dataSource.driverClassName': 'org.h2.Driver',
                'dataSource.username': 'sa',
                'dataSource.password': ''
        ]

        HibernateDatastore datastore = new HibernateDatastore( configuration, IrisG)

        IrisG.withTransaction { status ->  //status.setRollbackOnly()to rollback
            iris.each{
                row ->
                    new IrisG(*row.toList()).save()
            }
            def results = IrisG.list()
            //println results
            //groupby
            def c = IrisG.createCriteria()
            results = c.list{
                projections{
                    sqlGroupProjection 'name, max(sl) as max_sl', 'name',['name','max_sl'],[STRING,DOUBLE]
                }
            }

            println results.flatten()
        }

        datastore.close()

    }
}