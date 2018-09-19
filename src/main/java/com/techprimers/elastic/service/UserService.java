package com.techprimers.elastic.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Service;

import com.techprimers.elastic.model.User;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/*
 * We use TransportClient to create a communication layer between our application and the port 9300.
 * The type = "permanent" is what we use to search data in ElasticSearch; in the present case, we need to
 * use http://127.0.0.1:9200/employee/permament/<id value>, because the type we defined is "permanent",
 * and so this has to follow in the hierarchy just after employee.
 */
@Service
public class UserService {
	private TransportClient client;

	public UserService() throws UnknownHostException {
		client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
	}

	/*
	 * We can look for all the inserted users:
	 * http://127.0.0.1:9200/employee/_search/?size=1000&pretty=false.
	 * To look for a specific user:
	 * http://127.0.0.1:9200/employee/permanent/<id value>
	 * 
	 */
	public String saveUser(User user) throws IOException {
		// This can also be done invoking client.index(IndexRequest)
		IndexResponse response = client.prepareIndex("employee", "permanent", String.valueOf(System.currentTimeMillis()))
				.setSource(jsonBuilder().startObject().field("name", user.getName()).field("team", user.getTeam())
						.field("salary", user.getSalary()).endObject())
				.get();
		return response.toString();
	}

	public String updateUser(long id, String gender) throws IOException, ExecutionException, InterruptedException {
		Map<String, Object> jsonUser = getUser(id);
		if (jsonUser != null) {
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index("employee").type("permanent").id(String.valueOf(id))
					.doc(jsonBuilder().startObject().field("gender", gender).endObject());
			UpdateResponse updateResponse = client.update(updateRequest).get();
			return updateResponse.toString();
		}
		return null;
	}

	public String deleteUser(long id) {
		// This can also be done via DeleteRequest
		DeleteResponse deleteResponse = client.prepareDelete("employee", "permanent", String.valueOf(id)).get();
		return deleteResponse.toString();
	}

	public Map<String, Object> getUser(long id) {
		GetResponse getResponse = client.prepareGet("employee", "permanent", String.valueOf(id)).get();
		return getResponse.getSource();
	}
}
