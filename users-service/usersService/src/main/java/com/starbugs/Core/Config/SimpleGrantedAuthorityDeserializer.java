package com.starbugs.Core.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleGrantedAuthorityDeserializer extends JsonDeserializer<List<GrantedAuthority>>{


	

	@Override
	public List<GrantedAuthority> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		   ObjectMapper mapper = (ObjectMapper) p.getCodec();
	        JsonNode jsonNode = mapper.readTree(p);
	        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

	        Iterator<JsonNode> elements = jsonNode.elements();
	        while (elements.hasNext()) {
	            JsonNode next = elements.next();
	            JsonNode authority = next.get("authority");
	            grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
	        }
	        return grantedAuthorities;
	}

}
