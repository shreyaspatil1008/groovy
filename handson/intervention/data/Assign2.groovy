Q: Write one HTTP REST server (using ratpack)

This is yahoo weather server.
But yahoo weather does not support public REST api now-a-days
You can create API key and then subscribe and the query REST weather 

Howeever, we would use  below mock endpoints

Mock Yahoo weather EndPoints :
Three query parameters 
q : contains location variable 
format : json or xml 
u : for unit "c" or "f" 
Final URL is given below (Note, Get params need URLEncoding)
http://weatherapi1971.pythonanywhere.com/query.yahooapis.com/v1/public/yql?q=select* from weather.forecast where woeid in (select woeid from geo.places(1) where text='mumbai') and u='c'&format=json



Your server endpoints 
http://localhost:5050/<location>?format=json&unit=c 
<location> - platholder for location 
format and unit can be optional, default is json and c respectively 

Server should return below 
for format json :
{ "date_from_yahoo_api":{"text": .., "high":..., "low": ..}, "another_date_from_yahoo_api": {...} .. }
Repeat as many times as present in Mock Yahoo weather results 


for format xml:
where weather repeats as many times as present in Mock Yahoo weather results 
with chnaging dates 
<forecasts>
        <weather date=date_from_yahoo_api>
        <text>  </text>
        <high>  </high>
        <low>   </low>
        </weather>
        ... other dates 
    </forecasts>

Requirements 
- When user access your server endpoints 
your server query yahoo API (mock) and get the data 
and sends modified either json and xml back to user 