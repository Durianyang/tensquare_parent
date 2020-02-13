package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Durian
 * Date: 2020/2/13 20:49
 * Description: 交友业务
 * 交友业务逻辑：
 * （1）当用户登陆后在推荐好友列表中点击“心”，表示喜欢此人 ，在数据库tb_friend表中插入一条数据，islike 为0
 * （2）当你点击了喜欢过的人，也喜欢了你 , 表示互粉成功！也向tb_friend表中插入一条数据，islike为1 ，
 * 并且将你喜欢她的数据islike也修改为1
 * （3）当你点击了不喜欢某人（点击了叉），向tb_nofriend添加记录.
 * （4）当两个人互粉后，其中一人不喜欢对方了，删除好友表中的记录 ，向非好友表中添加记录
 */
@Service
@Transactional
public class FriendService
{
    private final FriendDao friendDao;
    private final NoFriendDao noFriendDao;
    private final UserClient userClient;

    @Autowired
    public FriendService(FriendDao friendDao, NoFriendDao noFriendDao, UserClient userClient)
    {
        this.friendDao = friendDao;
        this.noFriendDao = noFriendDao;
        this.userClient = userClient;
    }

    public int addFriend(String userid, String friendid)
    {
        // 如果用户已经添加了这个好友，则不进行任何操作,返回
        if (friendDao.selectCount(userid, friendid) > 0)
        {
            return 0;
        }
        // 添加一条记录
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        // 向tb_friend加一条记录
        friendDao.save(friend);
        // 自己关注数+1 对方粉丝数+1
        userClient.updateFollows(userid, 1);
        userClient.updateFans(friendid, 1);
        // 判断对方是否喜欢你，如果喜欢，将islike设置为1
        if (friendDao.selectCount(friendid, userid) > 0)
        {
            // 两个用户对应的表都更新
            friendDao.updateLike(userid, friendid, "1");
            friendDao.updateLike(friendid, userid, "1");

        }
        // 如果屏蔽表中存在记录则删除记录
        if (noFriendDao.selectCount(userid, friendid) > 0)
        {
            noFriendDao.deleteNoFriend(userid, friendid);
        }
        return 1;
    }

    /**
     * 添加屏蔽用户
     *
     * @param userid   该用户id
     * @param friendid 该用户屏蔽的用户id
     */
    public int addNoFriend(String userid, String friendid)
    {
        // 如果用户已经屏蔽了这个用户，则不进行任何操作,返回
        if (noFriendDao.selectCount(userid, friendid) > 0)
        {
            return 0;
        }
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        // 若果关注了该用户，屏蔽后删除关注表的记录
        if (friendDao.selectCount(userid, friendid) > 0)
        {
            friendDao.deleteFriend(userid, friendid);
            // 同时自身的关注数-1, 关注后被屏蔽用户的粉丝数-1
            userClient.updateFollows(userid, -1);
            userClient.updateFans(friendid, -1);
        }
        // 如果被屏蔽用户关注了该用户，修改被关注用户的islike
        if (friendDao.selectCount(friendid, userid) > 0)
        {
            friendDao.updateLike(friendid, userid, "0");
        }
        return 1;
    }

    /**
     * 取消关注
     * @param userid 用户id
     * @param friendid 被取消关注的用户id
     */
    public void deleteFriend(String userid, String friendid)
    {
        friendDao.deleteFriend(userid, friendid);
        // 同时关注数-1 对方粉丝数-1
        userClient.updateFollows(userid, -1);
        userClient.updateFans(friendid, -1);
        // 更新被关注用户的islike状态
        friendDao.updateLike(friendid, userid, "0");
    }

}
