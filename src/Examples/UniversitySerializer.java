/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples;

import Examples.Student;
import Examples.University;
import java.io.*;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author rao
 */
public class UniversitySerializer {

    public static void main(String args[]) throws IOException {
        try {

            // assign path to the university Xml, 
            String path = "/Users/rao/NetBeansProjects/lab-exercises/week-02/ClientServerSockets/src/resources/university-xml.xml";

            // create an instance context class, to serialize/deserialize.
            JAXBContext jaxbContext = JAXBContext.newInstance(University.class);

            // Create a file input stream for the university Xml.
            FileInputStream stream = new FileInputStream(path);

            // deserialize university xml into java objects.
            University university = (University) jaxbContext.createUnmarshaller().unmarshal(stream);


            // Iterate through the collection of student object and print each student object in the form of Xml to console.
            ListIterator<Student> listIterator = university.students.listIterator();
            
            System.out.println("Printing student objects serailized into Xml");
            
            
            while (listIterator.hasNext()) {

                PrintStudentObject(listIterator.next());

            }

            // Serialize university object into xml.
            
            StringWriter writer = new StringWriter();

            // We can use the same context object, as it knows how to 
            //serialize or deserialize University class.
            jaxbContext.createMarshaller().marshal(university, writer);

            
            System.out.println("Printing serialized university Xml before saving into file!");
            
            // Print the serialized Xml to Console.
            System.out.println(writer.toString());
            
            
            // Finally save the Xml back to the file.
            SaveFile(writer.toString(), path);



        } catch (JAXBException ex) {
            Logger.getLogger(UniversitySerializer.class.getName()).log(Level.SEVERE, null, ex);
        }





    }

    private static void PrintStudentObject(Student student) {

        try {


            StringWriter writer = new StringWriter();

            // create a context object for Student Class
            JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);

            // Call marshal method to serialize student object into Xml
            jaxbContext.createMarshaller().marshal(student, writer);

            System.out.println(writer.toString());

        } catch (JAXBException ex) {
            Logger.getLogger(UniversitySerializer.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    private static void SaveFile(String xml, String path) throws IOException {


        File file = new File(path);

        // create a bufferedwriter to write Xml
        BufferedWriter output = new BufferedWriter(new FileWriter(file));

        output.write(xml);

        output.close();



    }
}
