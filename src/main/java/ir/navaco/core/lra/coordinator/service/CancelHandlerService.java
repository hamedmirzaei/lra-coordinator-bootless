package ir.navaco.core.lra.coordinator.service;

public interface CancelHandlerService {

    //TODO cancel all pending cancel requests at the startup of the application
    //there should be one background thread that periodically checks for new
    //cancel requests and based on retry-limit and timeout, do the proper action

    //TODO cancel LRA (call applicants) based on timeout, retry-limit and
    //     eurekaDiscovery => where is the right place to do it?
    //     make a thread to process a single lraApplicant, then do it for all concurrently

}
