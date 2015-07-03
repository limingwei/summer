# coding=utf-8
#!/usr/bin/python

from jython import IJythonService 

class JythonServiceImpl(IJythonService): 
    def sayHi(self, param): 
        return u"Jython中的返回值"