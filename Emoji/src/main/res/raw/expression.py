
import os
import json
import codecs

def expression2Json():
    path="d:\Documents\Desktop\sf_emoji_container\sf-emoji-sample-mm_jj"
    print "path: "+path
    
    fileContent=[]
    component=[]
    for parent,dirnames,filenames in os.walk(path):
        for fileName in  filenames:   
            expressionInfo={}
            if fileName.endswith(".gif"):
                content=fileName.replace(".gif","")
                component=content.split("_")
            elif fileName.endswith(".png"):
                content=fileName.replace(".png","")
                component=content.split("_")    
                
            if len(component) == 4:
                expressionInfo["id"]=component[0]
                expressionInfo["code"]=component[1]
                expressionInfo["groupId"]=component[2]
                expressionInfo["text"]=unicode(component[3],"gbk")
                expressionInfo["fullName"]=unicode(fileName,"gbk")
                fileContent.append(expressionInfo)
                component=[]
            else:
                print "component lenth is illegal,len: "+str(len(component))
    result={}
    result["emojiGroup"]=fileContent
    resultContent= json.dumps(result, ensure_ascii=False)
    print resultContent
    destinationPath=path+'\\thefile.txt'
    file_object = codecs.open(destinationPath,'w',"utf-8")
    file_object.write(resultContent)
    file_object.close()
            

expression2Json()
    
