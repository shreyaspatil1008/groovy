@Grab(group='org.modelcatalogue', module='spreadsheet-builder-poi', version='0.4.1')
@Grab(group='commons-codec', module='commons-codec', version='1.10')
@GrabExclude('org.codehaus.groovy:groovy-all')

import org.modelcatalogue.spreadsheet.query.poi.PoiSpreadsheetCriteria

// Find mean quantity, unit price, ext price for each other
// what percentage of the total order does each row represent?

File file = new File("C:/Groovy Training/handson/intervention/data/data/sales_transactions.xlsx")
def query = PoiSpreadsheetCriteria.FACTORY.forFile(file)
def content = query.query{
    sheet("Sheet1")
}.sheet
def rows = content.rows

@groovy.transform.Canonical
@groovy.transform.ToString(includeNames = true)
class Row{
    Long account
    String name
    Long order
    String sku
    Long quantity
    Double unit_price
    Double ext_price
}

def types = [Long.class, String.class , Long.class, String.class, Long.class,Double.class,Double.class]
def cells = rows.drop(1).collect{it.cells}
                        .collect{lc ->
                            new Row(*lc.withIndex().collect{c,i-> c.read(types[i])})
                        }

def mean(lst){

    return (lst.sum()/lst.size().toDouble()).round(2)
}

def process(Closure pm, lst, String... cols){
    return cols.collectEntries{col -> [col, pm(lst.collect{it."$col"})]}
}

cells.groupBy{it.order}
    .collectEntries{k, v->[k,process(this.&mean,v,"quantity","unit_price","ext_price")]}
    
def sum(lst){
    return lst.sum()
}

order_total = cells.groupBy{it.order}
                    .collectEntries{k,v-> [k, process(this.&sum, v, "ext_price")]}
                    
cells.collect{[it.order, (it.ext_price/order_total[it.order].ext_price).round(2)]}                    
           