package com.jfam.subarashii.services;

import com.jfam.subarashii.entities.Anime;
import com.jfam.subarashii.entities.User;
import com.jfam.subarashii.entities.UserList;
import com.jfam.subarashii.repositories.UserListRepository;
import com.jfam.subarashii.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserListService {

    @Autowired
    UserListRepository userListRepository;

    public List<UserList> createDefaultList(User user) {

        List<UserList> defaultList = Arrays.asList(
                new UserList(Constantes.DefaultList.A_VOIR, user, false),
                new UserList(Constantes.DefaultList.EN_COURS,  user, false),
                new UserList(Constantes.DefaultList.TERMINEE,  user, false),
                new UserList(Constantes.DefaultList.EN_ATTENTE,  user, false),
                new UserList(Constantes.DefaultList.FAVORIS,  user, false)
        );

        return userListRepository.saveAll(defaultList);
    }

    public UserList createCustomList(User user, String nameList) {
        UserList customList = new UserList(nameList, user, true);
        return userListRepository.save(customList);
    }

    public List<UserList> getCurrentUserList(User user) {
        List<UserList> userList = userListRepository.findAllByUser(user);
        return userList;
    }

    /***Récupère la liste de l'utilisateur
     * @param idUserList id de la liste
     * @param user l'utilisateur courant
     * @return si null la liste n'existe pas ou n'appartient pas à l'utilisateur
     */
    public UserList getOneUserListByIdForCurrentUser(Long idUserList, User user) {

        // check si existe grace à l'id user list and anime
        UserList userList = userListRepository.findByIdAndUser(idUserList, user);

        if (userList == null)
            return null;
        return userList;
    }

    public UserList addAnimeToUserList(Anime animeToAdd, List<Anime> animeListInUserList, UserList currentUserList) {
        animeListInUserList.add(animeToAdd);
        currentUserList.setAnimes(animeListInUserList);
        return userListRepository.save(currentUserList);
    }

}
