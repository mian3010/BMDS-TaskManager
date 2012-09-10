/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Serialization class for university XML
 * @author rao
 */
@XmlRootElement(name = "university")
public class University {

    @XmlElementWrapper(name = "students")
    @XmlElement(name = "student")
    public List<Student> students;
    
    
    // A university may contain many other collections and entities  
    // but we don't condsider them here...
}
