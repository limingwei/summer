package jython;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

/**
 * @author li
 * @version 1 (2015年6月29日 下午1:18:44)
 * @since Java7
 */
public class JythonDemo {
    public static void main(String[] args) {
        PythonInterpreter pythonInterpreter = new PythonInterpreter();
        PySystemState pySystemState = Py.getSystemState();
        pySystemState.path.add("D:\\jython2.5.2\\Lib");
        pythonInterpreter.execfile("src/test/java/jython/JythonServiceImpl.py");
        pythonInterpreter.exec("userService=JythonServiceImpl()");
        PyObject pyObject = pythonInterpreter.get("userService");
        IJythonService jythonService = (IJythonService) pyObject.__tojava__(IJythonService.class);
        System.err.println(jythonService.sayHi("java传入参数"));
    }
}