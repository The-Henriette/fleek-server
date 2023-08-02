package run.fleek.domain.chat;

import com.querydsl.core.types.Projections;
import run.fleek.configuration.database.FleekQueryDslRepositorySupport;
import run.fleek.domain.chat.vo.ProfileChatVo;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.QProfile;

import java.util.Optional;

public class ProfileChatRepositoryImpl extends FleekQueryDslRepositorySupport implements ProfileChatRepositoryCustom {

  QProfile qSenderProfile = new QProfile("senderProfile");
  QProfile qReceiverProfile = new QProfile("receiverProfile");

  QProfileChat qProfileChatSender = new QProfileChat("profileChatSender");
  QProfileChat qProfileChatReceiver = new QProfileChat("profileChatReceiver");

  QChat qChat = QChat.chat;

  public ProfileChatRepositoryImpl() {
    super(ProfileChat.class);
  }

  @Override
  public Optional<ProfileChatVo> findBySenderAndReceiverName(String senderName, String receiverName) {
    return Optional.ofNullable(from(qProfileChatSender)
      .innerJoin(qProfileChatSender.profile, qSenderProfile)
      .innerJoin(qProfileChatSender.chat, qChat)
      .innerJoin(qProfileChatReceiver).on(qChat.chatId.eq(qProfileChatReceiver.chat.chatId))
      .innerJoin(qProfileChatReceiver.profile, qReceiverProfile)
      .where(qProfileChatSender.profile.profileName.eq(senderName)
        .and(qProfileChatReceiver.profile.profileName.eq(receiverName)))
      .select(Projections.constructor(ProfileChatVo.class,
        qSenderProfile.profileId,
        qSenderProfile.profileName,
        qSenderProfile.chatProfileKey,
        qProfileChatSender.anonymousChatKey,
        qProfileChatSender.profileChatCode,
        qReceiverProfile.profileId,
        qReceiverProfile.profileName,
        qReceiverProfile.chatProfileKey,
        qChat.chatUri))
      .fetchOne());
  }

  @Override
  public Optional<ProfileChat> findProfileByProfileChatCode(String profileChatCode) {
    return Optional.ofNullable(
      from(qProfileChatSender)
        .innerJoin(qProfileChatSender.profile, qSenderProfile).fetchJoin()
        .innerJoin(qProfileChatSender.chat, qChat).fetchJoin()
        .where(qProfileChatSender.profileChatCode.eq(profileChatCode))
        .select(qProfileChatSender)
        .fetchOne()
    );
  }
}
