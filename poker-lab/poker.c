#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "poker.h"

#include <ctype.h>

//Global variable for convenience
int handLength = 5; 

/* converts a hand (of 5 cards) to a string representation, and stores it in the
 * provided buffer. The buffer is assumed to be large enough.
 */
void hand_to_string (hand_t hand, char *handstr) {
    char *p = handstr;
    int i;
    char *val, *suit;
    for (i=0; i<handLength; i++) {
        if (hand[i].value < 10) {
            *p++ = hand[i].value + '0';
        } else {
            switch(hand[i].value) {
            case 10: *p++ = 'T'; break;
            case 11: *p++ = 'J'; break;
            case 12: *p++ = 'Q'; break;
            case 13: *p++ = 'K'; break;
            case 14: *p++ = 'A'; break;
            }
        }
        switch(hand[i].suit) {
        case DIAMOND: *p++ = 'D'; break;
        case CLUB: *p++ = 'C'; break;
        case HEART: *p++ = 'H'; break;
        case SPADE: *p++ = 'S'; break;
        }
        if (i<=3) *p++ = ' ';
    }
    *p = '\0';
}

/* converts a string representation of a hand into 5 separate card structs. The
 * given array of cards is populated with the card values.
 */
void string_to_hand (const char *handstr, hand_t hand) {

	const char *temp = handstr;
	int i = 0; 

	while (*temp != '\0'){

		if (*temp == ' ') temp++; 
		else{
			if (isdigit(*temp)){
				hand[i].value = *temp - '0';
				temp++;
			} else{
				if (isalpha(*temp)){
					switch (*temp){
						case 'A': hand[i].value = 14; break;
						case 'T': hand[i].value = 10; break;
						case 'J': hand[i].value = 11; break;
						case 'Q': hand[i].value = 12; break;
						case 'K': hand[i].value = 13; break;
					}
					temp++;
				}
			}

			switch (*temp){
				case 'C': hand[i].suit = CLUB; break;
				case 'D': hand[i].suit = DIAMOND; break;
				case 'H': hand[i].suit = HEART; break;
				case 'S': hand[i].suit = SPADE; break;
			}

			temp++;
			i++;
		}
	}
}

/* sorts the hands so that the cards are in ascending order of value (two
 * lowest, ace highest */
void sort_hand (hand_t hand) {

	int i, ci, j; 
	card_t temp;

	for (i=0; i<handLength; i++){
		ci = i; 

		for (j=ci+1; j<handLength; j++){
			if (hand[j].value < hand[ci].value) ci = j; 
		}

		temp = hand[ci];
		hand[ci] = hand[i];
		hand[i] = temp; 
	}

}

int count_pairs (hand_t hand) {

	int i; 
	int pairNum = 0;  
	int arr[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	sort_hand(hand); 

	for(i=0; i<handLength; i++){
		arr[hand[i].value]++;
	}

	for(i=0; i<15; i++){
		if (arr[i] >= 2) pairNum++;
	}

    	return pairNum; 
}

int is_onepair (hand_t hand) {

	int numPairs = count_pairs(hand);

	if (numPairs >= 1) return 1; 
	else return 0; 
}

int is_twopairs (hand_t hand) {

	int numPairs = count_pairs(hand);

	if (numPairs >= 2) return 1; 
	else return 0; 	

}

int is_threeofakind (hand_t hand) {

	int i; 
	int arr[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	sort_hand(hand);

	for (i=0; i<handLength; i++){
		arr[hand[i].value]++; 
	}

	for (i=0; i<15; i++){
		if (arr[i] >= 3) return 1; 
	}	

	return 0; 
}

int is_straight (hand_t hand) {

	int i; 

	sort_hand(hand);

	if (hand[4].value == 14){
		if (hand[0].value == 2 || hand[0].value == 10){
			for (i=1; i<handLength-1; i++){
				if (hand[i].value != hand[i-1].value+1) return 0;
			}
			//return 1;
			if (hand[0].value == 10) return 2; 
			else return 1; 
		}
	} else {
		for (i=1; i<handLength; i++){
			if (hand[i].value != hand[i-1].value+1) return 0; 
		}

		return 1;
	}

	return 0; 
}

int is_fullhouse (hand_t hand) {

	if (is_threeofakind(hand) && is_twopairs(hand) && !is_fourofakind(hand)) return 1; 
	else return 0; 

}

int is_flush (hand_t hand) {

	int i; 

	for(i=1; i<handLength; i++){
		if (hand[i].suit != hand[i-1].suit) return 0; 
	}

	return 1; 
}

int is_straightflush (hand_t hand) {
	
	if (is_straight(hand) && is_flush(hand)) return 1; 
	else return 0;

}

int is_fourofakind (hand_t hand) {

	int i; 
	int arr[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};	
	
	sort_hand(hand);

	for (i=0; i<handLength; i++){
		arr[hand[i].value]++;
	}

	for (i=0; i<15; i++){
		if (arr[i] == 4) return 1;
	}
	
	return 0; 
}

int is_royalflush (hand_t hand) {

	sort_hand(hand);

	if (is_flush(hand) && is_straight(hand)){
		if (hand[0].value == 10 && hand[4].value == 14) return 1; 
	}

	return 0; 

}

/* compares the hands based on rank -- if the ranks (and rank values) are
 * identical, compares the hands based on their highcards.
 * returns 0 if h1 > h2, 1 if h2 > h1.
 */
int compare_hands (hand_t h1, hand_t h2) {

	sort_hand(h1);
	sort_hand(h2); 

	hand_t handArr[] = {h1, h2};
	int score[] = {0, 0};  

	int i; 

	for (i=0; i<2; i++){
		if (is_royalflush(handArr[i])) score[i] = 9; 
		else{
		if (is_straightflush(handArr[i])) score[i] = 8; 
		else{
		if (is_fourofakind(handArr[i])) score[i] = 7; 
		else{
		if (is_fullhouse(handArr[i])) score[i] = 6; 
		else{
		if (is_flush(handArr[i])) score[i] = 5; 
		else{
		if (is_straight(handArr[i])) score[i] = 4; 
		else{
		if (is_threeofakind(handArr[i])) score[i] = 3; 
		else{
		if (is_twopairs(handArr[i])) score[i] = 2; 
		else{
		if (is_onepair(handArr[i])) score[i] = 1; 
		}}}}}}}}
	}

	if (score[0] == score[1]){	
		if (score[0] == 4){
			if (is_straight(h1) > is_straight(h2)) return 0; 
			else return 1; 
		} else return compare_highcards(h1, h2);
	} else{
		if (score[0] > score[1]) return 0; 
		else return 1; 
	}

}

/* compares the hands based solely on their highcard values (ignoring rank). if
 * the highcards are a draw, compare the next set of highcards, and so forth.
 */
int compare_highcards (hand_t h1, hand_t h2) {

	sort_hand(h1);
	sort_hand(h2);

	int i; 

	for (i=4; i>=0; i--){
		if (h1[i].value > h2[i].value) return 0; 
		if (h1[i].value < h2[i].value) return 1; 
	}

}
