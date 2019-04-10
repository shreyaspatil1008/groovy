package examples

import org.apache.poi.xssf.usermodel.XSSFCell
import builders.dsl.spreadsheet.api.Row
import builders.dsl.spreadsheet.query.api.SpreadsheetCriteria
import builders.dsl.spreadsheet.query.poi.PoiSpreadsheetCriteria

class Main{
    static void main(String[] args){
        readData()
	}

    static void  readData() {
        def path = "Nifty.xlsx";
        File file = new File(path)
        SpreadsheetCriteria query = PoiSpreadsheetCriteria.FACTORY.forFile(file)
        Collection rows = query.query({
            sheet {
                row{
                    cell('A'){

                    }
                }
            }

        }).rows
        def map = [:]

        def count = 0
        for(Row row:rows){
            if(count!=0){
                XSSFCell cell = row.cells.getAt(0).getCell()
                def value =  cell.toString().reverse().take(4).reverse()
                def valList = []
                if(value in map){
                    valList = map[value]
                }
                def cellVal = row.cells.getAt(1).getValue()
                if(valList.size() == 0){
                    valList[0] = cellVal
                }else{
                    valList.add(cellVal)
                }
                map[value] = valList
            }
            count = 1
        }
        def valueDiffMap=[:]
        map.each{
            valueDiffMap[ it.key] =(it.value.max() - it.value.min())
        }
        print "Year: "+valueDiffMap.max{it.value}.key
        println "------ Difference: "+valueDiffMap.max{it.value}.value
    }
}