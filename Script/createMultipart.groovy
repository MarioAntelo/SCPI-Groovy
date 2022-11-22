import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
    String CRLF = "\r\n";
    //Body
	 byte[] fileBytes = message.getBody(byte[]);
	 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	 
	//properties
	def mapProperties = message.getProperties();	
	def filename = mapProperties.get("filename");
	def eventid = mapProperties.get("eventId");
	def supplierid = mapProperties.get("supplierId");
	def operation = mapProperties.get("operation");
	def boundary = mapProperties.get("boundary");
	def disposition = 'Content-Disposition: form-data; ';
	
	//Create multipart---form-data
	String eventPart     = '--' + boundary + CRLF + disposition + 'name="eventId"'+ CRLF +CRLF + eventid + CRLF;
	String supplierPart  = '--' + boundary + CRLF + disposition + 'name="supplierId"' + CRLF +CRLF + supplierid + CRLF;
	String operationPart = '--' + boundary + CRLF + disposition + 'name="operation"'+ CRLF +CRLF  + operation + CRLF;
	String filePart		 = '--' + boundary + CRLF + disposition + 'name="file"; filename="' + filename + '"'+ CRLF + 'Content-Type: application/vnd.ms-excel' + CRLF +CRLF ;
	String endPart		 = CRLF + CRLF + '--' + boundary + '--';
	
	
	byte[]  eventPartBytes = eventPart.getBytes();
    byte[]  supplierPartBytes = supplierPart.getBytes();
	byte[]  operationPartBytes = operationPart.getBytes();
	byte[]  filePartBytes = filePart.getBytes();
	byte[]  endPartBytes = endPart.getBytes();
	
	//Write Bytes in Message
    outputStream.write(eventPartBytes);
    outputStream.write(supplierPartBytes);
    outputStream.write(operationPartBytes);
	outputStream.write(filePartBytes);
	outputStream.write(fileBytes);
	outputStream.write(endPartBytes);
    message.setBody(outputStream);
    
	//Create Headers
    long payloadSize = outputStream.size();
    String PayloadSize = payloadSize.toString();
    message.setHeader("Content-Length", PayloadSize);
    
    message.setHeader('Content-Type', "multipart/form-data; boundary=$boundary")

    return message;
}