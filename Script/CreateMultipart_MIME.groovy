import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

import javax.activation.DataHandler;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.BodyPart;
import javax.mail.util.ByteArrayDataSource;

***** IMPORTANT
** boundary defined causes http 400
def Message processData(Message message) {
    //Body
	 byte[] bytes = message.getBody(byte[]);
	 
	//properties
	def mapProperties = message.getProperties();	
	def filename = mapProperties.get("filename");
	def eventid = mapProperties.get("eventId");
	def supplierid = mapProperties.get("supplierId");
	def operation = mapProperties.get("operation");
	
	  
	// Create eventID
    BodyPart eventPart = new MimeBodyPart();
    eventPart.setText(eventid);
    eventPart.setDisposition('form-data; name="eventId"');
	
	// Create supplierId
    BodyPart supplierPart = new MimeBodyPart();
    supplierPart.setText(supplierid);
    supplierPart.setDisposition('form-data; name="supplierId"');
	
	// Create operation
    BodyPart operationPart = new MimeBodyPart();
    operationPart.setText(operation);
    operationPart.setDisposition('form-data; name="operation"');
	
    //  Construct File
    MimeBodyPart bodyFile = new MimeBodyPart();
    ByteArrayDataSource dataSource = new ByteArrayDataSource(bytes, 'application/vnd.ms-excel');;
    DataHandler byteDataHandler = new DataHandler(dataSource);
    bodyFile.setDataHandler(byteDataHandler);
    bodyFile.setFileName(filename);
    bodyFile.setDisposition('form-data; name="file"');

	//Build Multipart
    MimeMultipart multipart = new MimeMultipart();
    String boundary = (new ContentType(multipart.contentType)).getParameter('boundary');
    MimeMultipart multipart2 = (new ContentType(multipart.contentType)).setParameter('boundary', 'CPI');
	multipart2.addBodyPart(eventPart);
	multipart2.addBodyPart(supplierPart);
	multipart2.addBodyPart(operationPart);
    multipart2.addBodyPart(bodyFile);
	
	//multipart.setHeader("Content-Type", "multipart/form-data; boundary=\"CPI\"");

    // Set multipart into body
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    multipart2.writeTo(outputStream);
    message.setBody(outputStream.toByteArray());
	
	
    // Set Content type with bounday	
    message.setHeader('Content-Type', "multipart/form-data; boundary=${boundary}")

    return message;
}