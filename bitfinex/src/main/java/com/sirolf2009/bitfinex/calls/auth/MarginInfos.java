package com.sirolf2009.bitfinex.calls.auth;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.http.HttpResponse;

import com.sirolf2009.bitfinex.exceptions.BitfinexCallException;
import com.sirolf2009.bitfinex.util.CallUtils;

public class MarginInfos extends HttpBitfinex {

	public MarginInfos() {
		super("/margin_infos");
	}
	
	@Override
	public Object parseResponse(HttpResponse response) throws BitfinexCallException {
		return CallUtils.parseResponse(response, MarginInfosResponse.class);
	}
	
	public static class MarginInfosResponse implements Serializable {
		
		private static final long serialVersionUID = -8211497276524489351L;
		
		private double margin_balance;
		private double tradable_balance;
		private double unrealized_pl;
		private double unrealized_swap;
		private double net_value;
		private double required_margin;
		private double leverage;
		private double margin_requirements;
		private MarginInfosLimit[] margin_limits;
		
		@Override
		public String toString() {
			return "MarginInfosResponse [margin_balance=" + margin_balance + ", tradable_balance=" + tradable_balance
					+ ", unrealized_pl=" + unrealized_pl + ", unrealized_swap=" + unrealized_swap + ", net_value="
					+ net_value + ", required_margin=" + required_margin + ", leverage=" + leverage
					+ ", margin_requirements=" + margin_requirements + ", margin_limits="
					+ Arrays.toString(margin_limits) + "]";
		}
		
		public double getMargin_balance() {
			return margin_balance;
		}
		public void setMargin_balance(double margin_balance) {
			this.margin_balance = margin_balance;
		}
		public double getTradable_balance() {
			return tradable_balance;
		}
		public void setTradable_balance(double tradable_balance) {
			this.tradable_balance = tradable_balance;
		}
		public double getUnrealized_pl() {
			return unrealized_pl;
		}
		public void setUnrealized_pl(double unrealized_pl) {
			this.unrealized_pl = unrealized_pl;
		}
		public double getUnrealized_swap() {
			return unrealized_swap;
		}
		public void setUnrealized_swap(double unrealized_swap) {
			this.unrealized_swap = unrealized_swap;
		}
		public double getNet_value() {
			return net_value;
		}
		public void setNet_value(double net_value) {
			this.net_value = net_value;
		}
		public double getRequired_margin() {
			return required_margin;
		}
		public void setRequired_margin(double required_margin) {
			this.required_margin = required_margin;
		}
		public double getLeverage() {
			return leverage;
		}
		public void setLeverage(double leverage) {
			this.leverage = leverage;
		}
		public double getMargin_requirements() {
			return margin_requirements;
		}
		public void setMargin_requirements(double margin_requirements) {
			this.margin_requirements = margin_requirements;
		}
		public MarginInfosLimit[] getMargin_limits() {
			return margin_limits;
		}
		public void setMargin_limits(MarginInfosLimit[] margin_limits) {
			this.margin_limits = margin_limits;
		}
		
	}
	
	public static class MarginInfosLimit implements Serializable {
		
		private static final long serialVersionUID = -9036282357145432508L;
		
		private String on_pair;
		private double initial_margin;
		private double margin_requirement;
		private double tradable_balance;
		
		public String getOn_pair() {
			return on_pair;
		}
		public void setOn_pair(String on_pair) {
			this.on_pair = on_pair;
		}
		public double getInitial_margin() {
			return initial_margin;
		}
		public void setInitial_margin(double initial_margin) {
			this.initial_margin = initial_margin;
		}
		public double getMargin_requirement() {
			return margin_requirement;
		}
		public void setMargin_requirement(double margin_requirement) {
			this.margin_requirement = margin_requirement;
		}
		public double getTradable_balance() {
			return tradable_balance;
		}
		public void setTradable_balance(double tradable_balance) {
			this.tradable_balance = tradable_balance;
		}

	}

}
