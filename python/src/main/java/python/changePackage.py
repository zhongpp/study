
import os


def get_current_path(path='.'):
	path_str=os.path.abspath(path)
	print path_str
	pass

def find_file(keyword='',path='.'):
	fileArr={}
	for parent,dirnames,filenames in os.walk(path):
		for filename in filenames:
			if keyword in filename:
				rep_parent=parent
				rep_parent=rep_parent.replace('//','.')
				rep_parent=rep_parent.replace('\\','.')
				start=rep_parent.index('com')
				end=len(rep_parent)
				fileArr[os.path.join(parent,filename)]=rep_parent[start:end]	
	return fileArr

def rewrite_package(filename,content):
	data=open(filename,'rt').readlines()
	num=0
	with open(filename,'wt') as handle:
		if num==0:
			handle.writelines(content)
			handle.write('\n')
			++num
		handle.writelines(data[1:])
	


if __name__=='__main__':
	arr=find_file('.java','D://IdeaProjects//study-demo//src//main//java//com//zpp//java//basic//demo//java8//samples')
	for filename in arr:
		rewrite_package(filename,'package '+arr[filename]+';')
	