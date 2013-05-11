#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "graph.h"

int main (int argc, char *argv[]) {

	/* Chek user input */
	adj_vertex_t *adj_v; 

	if (argc == 1 || (argc - 1) % 3 != 0){ 
		return 0; 
	}

	vertex_t *vp = NULL, *vlist_head = NULL, *vlist_end = NULL;  

	/* Populate the vertext list from input */
	int i; 
	for (i = 1; i < argc; i+=3){
		
		if (isalpha(*argv[i]) && isalpha(*argv[i+1]) && isdigit(*argv[i+2])){
			add_edge(&vp, argv[i], argv[i+1], atoi(argv[i+2]));
		
			if (vp->next == NULL){ 
				vlist_head = vp; 
			} else {
				vlist_head = vp->next;
			}

			if (i == 1) vlist_end = vp;
		       	else vp = vlist_end; 	

			//printf("Vertex Curr: %s \n", vp->name); 
			//printf("Vertex Head: %s \n", vlist_head->name); 
		} else 
			printf("\nINPUT ERROR: Vertex '%s %s %s' is of incorrect format.\n", argv[i], argv[i+1], argv[i+2]); 

	}

	vlist_head = vlist_end; 

	/* Printing adjacency list */ 
	printf("\nAdjacency list:\n");
	for (vp = vlist_head; vp != NULL; vp = vp->next) {
		printf("  %s: ", vp->name);
		for (adj_v = vp->adj_list; adj_v != NULL; adj_v = adj_v->next) {
			printf("%s(%d) ", adj_v->vertex->name, adj_v->edge_weight);
		}
		printf("\n");
	}

	/* Finding tours */ 
       	find_tour(vlist_end);

	/* Freeing the list */ 
	free_list(vlist_end); 
	
	return 0;
}

