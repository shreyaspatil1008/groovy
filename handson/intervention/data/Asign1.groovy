Q: Given Nifty-17_Years_Data-V1.xlsx
Which is 17 years of Nifty data with Open, Close etc index 
Find out the year when stock market was crashed
(ie, year where change in maximum and minimum of Open index was maximum accross all years) 
Use functional techniques 
The excel is present in data directory 

Other libs are used 
For CSV 
@Grab('com.xlson.groovycsv:groovycsv:1.3')
import static com.xlson.groovycsv.CsvParser.parseCsv
import com.xlson.groovycsv.CsvIterator

For Excel 
@Grab(group='org.modelcatalogue', module='spreadsheet-builder-poi', version='0.4.1')
@Grab(group='commons-codec', module='commons-codec', version='1.10')
@GrabExclude('org.codehaus.groovy:groovy-all')

import org.modelcatalogue.spreadsheet.query.poi.PoiSpreadsheetCriteria


///Boston.csv 
///Another example 
:Attribute Information (in order):
    - CRIM     per capita crime rate by town
    - ZN       proportion of residential land zoned for lots over 25,000 sq.ft.
    - INDUS    proportion of non-retail business acres per town
    - CHAS     Charles River dummy variable (= 1 if tract bounds river; 0 otherwise)
    - NOX      nitric oxides concentration (parts per 10 million)
    - RM       average number of rooms per dwelling
    - AGE      proportion of owner-occupied units built prior to 1940
    - DIS      weighted distances to five Boston employment centres
    - RAD      index of accessibility to radial highways
    - TAX      full-value property-tax rate per $10,000
    - PTRATIO  pupil-teacher ratio by town
    - B        1000(Bk - 0.63)^2 where Bk is the proportion of blacks by town
    - LSTAT    % lower status of the population
    - MEDV     Median value of owner-occupied homes in $1000s

"crim", "zn",   "indus", "chas", "nox",  "rm",  "age",  "dis",  "rad", "tax", "ptratio", "b",   "lstat","medv"
0.00632, 18,     2.31,    "0",    0.538,  6.575, 65.2,   4.09,  1,      296,   15.3,      396.9, 4.98,   24

1. read data in boston 

2. Create a column crimxmedv = crim X medv 

3. select columns crimxmedv , tax 

4. what is the max value of crim 


5. what is max value of medv 

5. select rows of crim where medv is max 


6. select rows of medv where crim is max 


7. how many unique value in chas and rad 
7.1 How many chas in  each of chas value 

8. what is max and min value of medv for each chas 


9. put crimxmedv and tax in csv 

///Excel 
sales_transactions.xlsx
Find mean quantity , unit price, ext price for each order 
What percentage of the  total order does each row represent?

///REST 
https://api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml&units=metric&appid=d86ca3beb3c8bf8cce8409a0da74b4c9
https://api.openweathermap.org/data/2.5/forecast?q=HYDERABAD,IN&mode=json&units=metric&appid=d86ca3beb3c8bf8cce8409a0da74b4c9


'''
//list.dt Time of data forecasted, unix, UTC
//JSON output 
{"city":{"id":1851632,"name":"Shuzenji",
"coord":{"lon":138.933334,"lat":34.966671},
"country":"JP",
"cod":"200",
"message":0.0045,
"cnt":38,
"list":[{
        "dt":1406106000,
        "main":{
            "temp":298.77,
            "temp_min":298.77,
            "temp_max":298.774,
            "pressure":1005.93,
            "sea_level":1018.18,
            "grnd_level":1005.93,
            "humidity":87,
            "temp_kf":0.26},
        "weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],
        "clouds":{"all":88},
        "wind":{"speed":5.71,"deg":229.501},
        "sys":{"pod":"d"},
        "dt_txt":"2014-07-23 09:00:00"}
        {"dt":....  }
        ...
]}
'''

'''     
//XML output 
<weatherdata>
<location><name>London</name><type/><country>US</country><timezone/><location altitude="0" latitude="39.8865" longitude="-83.4483" geobase="geonames" geobaseid="4517009"/></location>
<credit/>
<meta><lastupdate/><calctime>0.0028</calctime><nextupdate/></meta>
<sun rise="2015-06-30T10:08:46" set="2015-07-01T01:06:20"/>
<forecast>
 <time from="2015-06-30T09:00:00" to="2015-06-30T12:00:00">
 <symbol number="500" name="light rain" var="10n"/>
 <precipitation value="5" unit="3h" type="rain"/>
 <windDirection deg="253.5" code="WSW" name="West-southwest"/>
 <windSpeed mps="4.9" name="Gentle Breeze"/>
 <temperature unit="celsius" value="16.89" min="16.89" max="17.375"/>
 <pressure unit="hPa" value="989.51"/>
 <humidity value="96" unit="%"/>
 <clouds value="broken clouds" all="64" unit="%"/>
</time>
 <time from="2015-06-30T12:00:00" to="2015-06-30T15:00:00">
 <symbol number="500" name="light rain" var="10d"/>
 <precipitation value="99" unit="3h" type="rain"/>
 <windDirection deg="248.001" code="WSW" name="West-southwest"/>
 <windSpeed mps="4.86" name="Gentle Breeze"/>
 <temperature unit="celsius" value="17.23" min="17.23" max="17.614"/>
 <pressure unit="hPa" value="991.29"/>
 <humidity value="97" unit="%"/>
 <clouds value="scattered clouds" all="44" unit="%"/>
 </time>

...

</forecast>
</weatherdata>


'''