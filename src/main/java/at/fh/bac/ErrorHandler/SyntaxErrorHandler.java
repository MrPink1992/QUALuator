package at.fh.bac.ErrorHandler;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.security.sasl.SaslException;
import java.util.List;

public class SyntaxErrorHandler implements ErrorHandler {

    private List<Exception>  exceptions;


    public SyntaxErrorHandler() {
    }

    public SyntaxErrorHandler(List<Exception> exceptions) {
        this.exceptions = exceptions;

    }


    public List<Exception> getExceptions() {
        return this.exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public void warning(SAXParseException ex) throws SAXException {

        this.exceptions.add(ex);

        System.err.println("Following warning occured: " + ex.getMessage());
        System.err.println("Resuming validation... ");


    }

    public void error(SAXParseException ex) throws SAXException{

        this.exceptions.add(ex);
        System.err.println("Following error occured : " + ex.getMessage());
        System.err.println("Resuming validation... ");
    }

    public void fatalError(SAXParseException ex) throws SAXException {

        this.exceptions.add(ex);
        System.err.println("Following fatalerror occured : " + ex.getMessage());
        System.err.println("Resuming validation... ");
    }

}