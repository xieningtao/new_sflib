import os
import json
import codecs

def expressContainerEntry():
    path="d:\Documents\Desktop\sf_emoji_container"
    reultContent=[]
    for parent,dirnames,filenames in os.walk(path):
            for fileName in  dirnames:  
                print "fileName: "+fileName
                component=fileName.split("_")
                fileEntry={}
                fileEntry["fileName"]=fileName
                if len(component)==2:
                    fileEntry["fileId"]=component[0]
                    fileEntry["fileType"]=component[1]
                    reultContent.append(fileEntry)
                else:
                    print "component lenth is illegal,len: "+str(len(component))
    fileContent={}
    fileContent["emojiFileBeen"]=reultContent
    file_object = codecs.open(path+'\emojiFileBeen.txt','w',"utf-8")
    file_object.write(json.dumps(fileContent, ensure_ascii=False))
    file_object.close()    
                
expressContainerEntry()