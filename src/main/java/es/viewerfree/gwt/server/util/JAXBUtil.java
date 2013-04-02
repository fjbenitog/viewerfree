package es.viewerfree.gwt.server.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import es.viewerfree.gwt.server.dto.UserDtoList;
import es.viewerfree.gwt.shared.dto.UserDto;
import es.viewerfree.gwt.shared.dto.UserProfile;

public class JAXBUtil {
	
	public static String marshal(UserDtoList userDtoList) throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance(UserDtoList.class,UserProfile.class,UserDto.class);
		Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer = new StringWriter();
		marshaller.marshal(userDtoList, writer);
		return writer.toString();
	}
	
	public static UserDtoList unmarshal(String xml) throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance(UserDtoList.class,UserProfile.class,UserDto.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		return (UserDtoList) unmarshaller.unmarshal(reader);
	}
}
