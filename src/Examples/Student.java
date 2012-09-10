/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Serialization class for Student 
 * @author rao
 */

// Specify  XmlRootElement annotation, if you want to serailize a class into a standalone XML.

@XmlRootElement(name = "student")
public class Student  {
    
    @XmlAttribute
    public String id;
    
    @XmlAttribute
    public String name;
    
    // If you dont specify any annotation, it will be serialized as XmlElement.
    
    public String courses;
    
}
