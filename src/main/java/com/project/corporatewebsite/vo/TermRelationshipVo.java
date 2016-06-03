package com.project.corporatewebsite.vo;

public class TermRelationshipVo extends CommonVo{
	
	private String channel;
	private String ownerSeq;
	private String termSeq;
	private String taxonomy;

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOwnerSeq() {
		return ownerSeq;
	}
	public void setOwnerSeq(String ownerSeq) {
		this.ownerSeq = ownerSeq;
	}
	public String getTermSeq() {
		return termSeq;
	}
	public void setTermSeq(String termSeq) {
		this.termSeq = termSeq;
	}
	public String getTaxonomy() {
		return taxonomy;
	}
	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}	
	
	@Override
	public String toString() {
		return "TermRelationshipVo [channel=" + channel + ", ownerSeq=" + ownerSeq +  
				", termSeq =" + termSeq + ", taxonomy =" + taxonomy + 
				", getSeq()=" + getSeq() + ", getRegId()=" + getRegId() +
				", getRegIp()=" + getRegIp() + ", getRegDate()=" + getRegDate() + ", getModId()=" + getModId() +
				", getModIp()=" + getModIp() + ", getModDate()=" + getModDate() + ", getDelYn()=" + getDelYn() + "]";
	}	
}
