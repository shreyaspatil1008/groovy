import groovy.json.*
def s = new XmlSlurper()
def json = s.parse(new File("../../groovy-initial-reference/toBeShared/data/books.xml"))
json.value.books.book.findAll{it.@available.toInteger() >= 14}.author*.each{println it.@id}


//Building

import groovy.xml.*
def wr = new StringWriter()
builder = new MarkupBuilder(wr)

builder.books{
    book {
        author 'Shreyas'
        company{
            status(['published':'yes'],1970)
        }
    }
    book {
        author 'Tejas'
        company{
            status(['published':'no'],2019)
        }
    }    
    

}

def str = wr.toString()