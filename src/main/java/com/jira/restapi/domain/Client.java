/*
 * Client Copyright (C) 2015 Nishimura Software Studio
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.jira.restapi.domain;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.URI;


import org.springframework.stereotype.Controller;

import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;



@Controller
public class Client implements Serializable {

  private static final long serialVersionUID = 4L;

  private static HttpTransport httpTransport = new NetHttpTransport();

  private String user;

  private String password;

  /**
   * Constructs this object with no property values.
   */
  public Client() {}

  /**
   * Constructs this object with a user name and a password for Basic authentication. This
   * constructor is equivalent to the default one followed by calls to {@link #setUser} and
   * {@link #setPassword}.
   * 
   * @param user user name
   * @param password password
   * @since 4.0
   */
  public Client(String user, String password) {
    setUser(user);
    setPassword(password);
  }

  /**
   * Returns the HTTP transport.
   * 
   * @return HTTP transport
   * @since 4.0
   */
  protected static HttpTransport getHttpTransport() {
    return httpTransport;
  }

  /**
   * Returns the user name for Basic authentication.
   * 
   * @return user name
   * @since 4.0
   */
  public String getUser() {
    return user;
  }

  /**
   * Returns the password for Basic authentication.
   * 
   * @return password
   * @since 4.0
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the user name for Basic authentication.
   * 
   * @param user user name to be set
   * @since 4.0
   */
  public void setUser(String user) {
    this.user = user;
  }

  /**
   * Sets the password for Basic authentication.
   * 
   * @param password password to be set
   * @since 4.0
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns a Bitbucket REST API service. If both the user name and the password is not
   * <code>null</code>, they are used for Basic authentication.
   * 
   * @return Bitbucket REST API service
   */
  public Service getService() {
    if (user != null && password != null) {
      return getService(user, password);
    }
    return new RestService(null);
  }

  /**
   * Returns a Bitbucket REST API service with a user name and a password for Basic authentication.
   * 
   * @param user user name
   * @param password password
   * @return Bitbucket REST API service
   * @throws NullPointerException if either the user name or the password is <code>null</code>
   * @since 4.0
   */
  public Service getService(String user, String password) {
    return new RestService(new BasicAuthentication(user, password));
  }

  /**
   * Bitbucket REST API service.
   * 
   * @author Kaz Nishimura
   * @since 2.0
   */
  public class RestService extends Service {

    private static final String API_ROOT = "https://emagine-reality.atlassian.net/";


    // private HttpRequestFactory requestFactory;

    /**
     * HTTP request interceptor for authentication.
     */
    private final HttpExecuteInterceptor authentication;

    /**
     * Cached current user of the Bitbucket REST API.
     * 
     * @since 3.0
     */

    /**
     * Constructs this object with a HTTP execute interceptor for authentication.
     * 
     * @param authentication HTTP execute interceptor
     */
    public RestService(HttpExecuteInterceptor authentication) {
      this.authentication = authentication;
    }

    /**
     * Returns the URI of an endpoint path.
     * 
     * @param path endpoint path
     * @return endpoint URI
     */
    @Override
    public URI getEndpoint(String path) {
      URI root = URI.create(API_ROOT);
      return root.resolve(URI.create(path));
    }


    /*
     * public URI getRepoEndpoint(String path, String userName) {
     * 
     * URI root = URI.create(API_ROOT + REPO_ENDPOINT_PATH + "/" + userName); return
     * root.resolve(URI.create(path)); }
     */

    /*
     * public static Future<Response> getAsyncHttp(final String url) { return
     * ClientBuilder.newClient().target(url).request().async().get(); }
     */

    @Override
    public HttpResponse executeURI(URI endpoint) throws IOException, InterruptedException {
      HttpResponse httpResponse = null;
      
      
      System.out.println(endpoint.toString());
      
      
      
      try {
        HttpRequest request =
            getHttpTransport().createRequestFactory().buildGetRequest(
                new GenericUrl(endpoint.toString()));
        request.setInterceptor(authentication);
        request.setThrowExceptionOnExecuteError(false);
        request.setConnectTimeout(60000);
        request.setReadTimeout(120000);
       

        return request.execute();
      } catch (HttpResponseException e) {
        System.out.println(e);
      } catch (SocketTimeoutException e) {
        System.out.println(e + ", URL : " + endpoint);
      }
      return httpResponse;
    }


    /**
     * Clears the cached current Bitbucket user.
     * 
     * @since 3.0
     */



  }
}
