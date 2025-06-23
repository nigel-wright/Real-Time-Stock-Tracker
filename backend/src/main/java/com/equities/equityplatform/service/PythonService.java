package com.equities.equityplatform.service;

import jep.JepConfig;
import jep.SubInterpreter;
import jep.JepException;
import org.springframework.stereotype.Service;

@Service
public class PythonService {

    private final JepConfig jepConfig;
    private final ThreadLocal<SubInterpreter> interpreterThreadLocal;

    public PythonService() throws JepException {
        // Get root project path
        String projectRoot = System.getProperty("user.dir");

        // Config jep, add paths to python folder and .venv package
        this.jepConfig = new JepConfig()
                .addIncludePaths(
                        projectRoot + "/src/main/python",
                        projectRoot + "/src/main/python/.venv/lib/python3.12/site-packages"
                );

        this.interpreterThreadLocal = ThreadLocal.withInitial(() -> {
            try {
                SubInterpreter interp = jepConfig.createSubInterpreter();

                interp.exec("import yahooAPI");
                interp.exec("print('yahooAPI module imported successfully')");

                return interp;
            } catch (JepException e) {
                throw new RuntimeException("Failed to start Python interpreter for thread: " +
                        Thread.currentThread().getName(), e);
            }
        });
    }

    private SubInterpreter getInterpreter() {
        return interpreterThreadLocal.get();
    }

    // 2-arg version; ticker, start date
    public Object fetchData(String ticker, String startDate) {
        return callFetch(ticker, startDate);
    }

    // 3-arg version; ticker, start date, end date
    public Object fetchData(String ticker, String startDate, String endDate) {
        return callFetch(ticker, startDate, endDate);
    }

    // 4-arg version; ticker, start date, end date, and interval 
    public Object fetchData(String ticker,
                          String startDate,
                          String endDate,
                          String interval) {
        return callFetch(ticker, startDate, endDate, interval);
    }

    // Final to make JEP call to process python
    private Object callFetch(Object... args) {
        try {
            SubInterpreter interp = getInterpreter();

            // Get the fetch data
            interp.exec("from yahooAPI import fetchdata");

            for (Object arg : args) {
                System.out.println("Arg is: " + arg);
            }

            // Return the result null/data
            return interp.invoke("fetchdata", args);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during Python execution", e);
        }
    }
}