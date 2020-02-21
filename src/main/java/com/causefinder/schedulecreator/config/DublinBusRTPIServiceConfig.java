package com.causefinder.schedulecreator.config;

import com.causefinder.schedulecreator.client.BusDataScrapperSoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class DublinBusRTPIServiceConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.causefinder.schedulecreator.rtpi.wsdl");
        return marshaller;
    }

    @Bean
    public BusDataScrapperSoapClient busDataScrapperSoapClient(Jaxb2Marshaller marshaller) {
        BusDataScrapperSoapClient client = new BusDataScrapperSoapClient();
        client.setDefaultUri("http://rtpi.dublinbus.ie/DublinBusRTPIService.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

/*    @Bean("messageFactory")
    public SaajSoapMessageFactory messageFactory() throws SOAPException {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return messageFactory;
    }*/

}