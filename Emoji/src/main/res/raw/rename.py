import os
def renameFile():
    path="d:\Documents\Desktop\sf_emoji_container\sf-emoji-sample-hh_hh"
    print "path: "+path
    id=100
    code=2000    
    for parent,dirnames,filenames in os.walk(path):    #�����������ֱ𷵻�1.��Ŀ¼ 2.�����ļ������֣�����·���� 3.�����ļ�����    
        for fileName in  filenames:                        #����ļ�����Ϣ
            id=id+1
            code=code+1
            if fileName.endswith(".gif"):     
                name=fileName.replace("@2x.gif","")
                newName=str(id)+"_"+str(code)+"_"+name+".gif"
                os.rename(os.path.join(parent, fileName), os.path.join(parent, newName))
            elif fileName.endswith(".png"):
                name=fileName.replace("@2x.png","")
                newName=str(id)+"_"+str(code)+"_"+name+".png"
                os.rename(os.path.join(parent, fileName), os.path.join(parent, newName))                

renameFile()