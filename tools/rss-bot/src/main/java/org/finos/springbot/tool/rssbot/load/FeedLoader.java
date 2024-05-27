package org.finos.springbot.tool.rssbot.load;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

import org.finos.springbot.tool.rssbot.ProxyProperties;
import org.finos.springbot.tool.rssbot.feed.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.Response.Status.Family;

public class FeedLoader {
	
	public static final Logger LOG = LoggerFactory.getLogger(FeedLoader.class);
	
	List<ProxyProperties> pp;
	
	public FeedLoader(List<ProxyProperties> proxy) {
		if ((proxy == null) ||  (proxy.size() == 0)) {
			ProxyProperties noProxy = new ProxyProperties();
			noProxy.setHost(ProxyProperties.NO_PROXY);
			this.pp = Collections.singletonList(noProxy);
		} else {
			this.pp = proxy;
			
		}
		
	}
	
	public SyndFeed createSyndFeed(Feed f) throws Exception {
		SyndFeedInput input = new SyndFeedInput();
		input.setAllowDoctypes(true);
		SyndFeed feed = input.build(new XmlReader(downloadContent(f.getUrl(), f.getProxy())));
		return feed;
	}
	
	public Feed createFeed(String url, String name) throws FeedException {
		Exception last = null;
		for (ProxyProperties proxyProperties : pp) {
			try {
				SyndFeedInput input = new SyndFeedInput();
				input.setAllowDoctypes(true);
				SyndFeed feed = input.build(new XmlReader(downloadContent(url, proxyProperties)));
				Feed f = new Feed();
				if (!StringUtils.hasText(name)) {
					f.setName(feed.getTitle());
				} else {
					f.setName(name);
				}
				f.setDescription(feed.getDescription());
				f.setUrl(url);
				f.setProxy(proxyProperties);
				return f;
			} catch (Exception e) {
				LOG.info("Couldn't get feed "+url+" with "+proxyProperties.getHost());
				last = e;
			}
		}

		throw new FeedException("Couldn't download feed with any proxy", last);
	}
	

	public InputStream downloadContent(String url, ProxyProperties pp) throws MalformedURLException {
		JerseyApiBuilder jab = new JerseyApiBuilder(url);
		
		if (pp != null) {
			pp.configure(jab);
		}
		
		WebTarget wt = jab.newWebTarget(url);
		Response r = wt.request().get();
		Status.Family fam = r.getStatusInfo().getFamily();
		if (fam == Family.SUCCESSFUL) {
			return r.readEntity(InputStream.class);
		}
		
		throw new MalformedURLException("Couldn't download: "+url+" status: "+r.getStatus());
	}}
