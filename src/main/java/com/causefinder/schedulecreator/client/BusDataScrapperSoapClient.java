package com.causefinder.schedulecreator.client;

import com.causefinder.schedulecreator.rtpi.wsdl.GetRealTimeStopData;
import com.causefinder.schedulecreator.rtpi.wsdl.GetRealTimeStopDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Slf4j
public class BusDataScrapperSoapClient extends WebServiceGatewaySupport {

    public GetRealTimeStopDataResponse getRealTimeStopDataResponse(Integer busStopId) {

        GetRealTimeStopData request = new GetRealTimeStopData();
        request.setStopId(busStopId);
        request.setForceRefresh(true);
        GetRealTimeStopDataResponse response = (GetRealTimeStopDataResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://rtpi.dublinbus.ie/DublinBusRTPIService.asmx", request,
                        new SoapActionCallback(
                                "http://dublinbus.ie/GetRealTimeStopData"));

        return response;
    }

}
