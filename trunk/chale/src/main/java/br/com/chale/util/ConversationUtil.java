package br.com.chale.util;

import javax.enterprise.context.Conversation;

public class ConversationUtil {

	public static void iniciarConversacao(Conversation conversation) {
		if (conversation.isTransient())
			conversation.begin();
	}
	
	public static void terminarConversacao(Conversation conversation) {
		if (!conversation.isTransient())
			conversation.end();
	}
	
}
