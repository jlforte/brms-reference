package com.rhc.brms.ref.core;

/*
 * Generic Interface to hide the rules solution behind
 * 
 */
public interface RulesApplicationInterface<Request, Response> {

   public Response executeAllRules( Request request );
   
}
