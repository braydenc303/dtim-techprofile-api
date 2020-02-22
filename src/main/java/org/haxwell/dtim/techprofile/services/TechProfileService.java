package org.haxwell.dtim.techprofile.services;

import java.util.List;

import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;
import org.haxwell.dtim.techprofile.entities.UserTechProfileLineItemScore;

public interface TechProfileService {

	public TechProfile get(Long id);
	public TechProfileTopic addTopic(String topicName);
	public TechProfileLineItem addLineItem(Long topicId, String lineItemName, String l0desc, String l1desc, String l2desc, String l3desc);
	public TechProfileLineItem updateLineItem(Long lineItemId, String lineItemName, String l0desc, String l1desc, String l2desc, String l3desc);
	public TechProfileTopic updateTopic(Long topicId, String name);
	public boolean updateSequencesRelatedToATopicAndItsLineItems(long[] arr);
	
	public List<UserTechProfileLineItemScore> getUserIdScores(Long userId);
	
	public List getQuestionCountsPerCell();
	public List getCorrectlyAnsweredQuestionCountsPerCell(Long userId);
}
