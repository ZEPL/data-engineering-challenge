## Social Graphs

### Introduction
A social graph consists of a set of users, and a set of relationships between two users.

So you may have something like:

- users = [Richard, Anthony, Mina, Zuny, Marc]
- friends = [{Richard, Anthony}, {Mina, Marc}, {Mina, Zuny}]

The above example is a social graph of 5 users, and 

- Richard is friends with Anthony
- Mina is friends with Marc and Zuny

They are bi-directional, it implies that

- Anthony is also friends with Richard
- Marc and Zuny are also friends with Mina

In social graphs, degrees mean the distance between two users.

- i.e. Richard and Anthony are direct friends, so the degree between them is 1.
- i.e. Marc and Zuny are not direct friends, but they are connected through Mina. (Marc -> Mina -> Zuny). So the degree between them is 2.
- i.e. Richard and Mina are not connected so there is no degree between those two.

### Problem 

We want to implement a social graph such that we can do the following action quickly and efficiently as possible:

Suppose we have two users: UserA, and UserB.

1. Find all first degree connections of UserA  (my direct friends). i.e.
```
User[] getFriends(User a);
```

2. Find if UserA and UserB are 1st degree connected (direct friends). 
```
boolean areFirstDegree(User a, User b);
```

3. Find if UserA and UserB are 2nd degree connected (friends of friends). 
```
boolean areSecondDegree(User a, User b);
```

4. Find if UserA and UserB are 3rd degree connected (friends of friends of friends). 
```
boolean areThirdDegree(User a, User b);
```

Some expectations:

- Expect 2 GB of memory 
- Expect around 1 M users
- Expect every user to have an average of 10 friends
- Expect that addition and removal of users and friends is infrequent
- It's okay if application start-up time is slow, as long as runtime is fast

Implement this however you would like, with what ever db storage that you would like.
However, consider the following additional follow-up discussion questions:

- How scalable is your app?
- What would you do if there's now 10 M users?
- What would you do if there's now 100 M users, and 100 friends per user on average?
- How fault tolerant and available would the solution be?
- How fast would it be?

We're looking for completeness, cleanliness and reason behind your decisions. 
Consider the representation of the graph and the efficiency of query and storage.

If you have any questions, please send them to rpark@zepl.com.

Thanks,
And good luck!
