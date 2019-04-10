//HTML
@Grab(group='org.ccil.cowan.tagsoup',module='tagsoup',version='1.2')
import org.ccil.cowan.tagsoup.Parser

def url = "http://www.google.com"
def str = url.toURL().text
def html = new XmlParser(new Parser()).parseText(str)
html.body.'**'.a.@href //.'**' means //