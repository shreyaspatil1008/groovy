//Get - Rest call

@Grab(group='io.github.http-builder-ng',module='http-builder-ng-core',version='1.0.3')
import static groovyx.net.http.HttpBuilder.configure
//Text, csv, xml, json,and html if jsoup is in path

def http = configure{
    request.uri = "http://httpbin.org"
}

def result = http.get{
    request.uri.path = "/get"
}

println result.headers // result is in json
//[Accept:text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2, Accept-Encoding:gzip, deflate, Host:httpbin.org, User-Agent:Java/1.8.0_40]

 result = http.post{
    request.uri.path = "/post"
    request.contentType = "application/json"
    request.body = [user:"XYZ",age:20]
}
println result.headers // result is in json
//[Accept:text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2, Accept-Encoding:gzip, deflate, Content-Length:23, Content-Type:application/json; charset=utf-8, Host:httpbin.org, User-Agent:Java/1.8.0_40]
