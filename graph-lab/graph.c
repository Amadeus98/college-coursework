#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "graph.h"

/* Allocates memory and sets name for a vertex */ 
void *alloc_vertex (char *name){

	vertex_t *v = (vertex_t *)malloc(sizeof(vertex_t)); 
	v->name = name; 
	v->next = NULL; 
	v->visited = 0; 

	return v; 
}

/* Allocates memory and sets weight for an adjacent vertex list */
void *alloc_adj_vertex (int edge_weight){

	adj_vertex_t *adj_v = (adj_vertex_t *)malloc(sizeof(adj_vertex_t)); 
	adj_v->edge_weight = edge_weight; 
	adj_v->next = NULL; 

	return adj_v; 
}

/* Adds another vertex to the map based on user input */ 
void add_edge (vertex_t **vtxhead, char *v1_name, char *v2_name, int weight) {
	
	/* Uncomment this during testing to see which vertices are being added 
	 * printf("\nAdding edge %s <-> %s (%d)\n", v1_name, v2_name, weight); 
	 */

	int newFlagv1 = 0; 
	int newFlagv2 = 0; 

	vertex_t *v1 = find_or_create_vertex(*vtxhead, v1_name, &newFlagv1); 
	vertex_t *v2 = find_or_create_vertex(*vtxhead, v2_name, &newFlagv2); 

	add_adj_vertex(v1, create_adj_vertex(v2, weight));
	add_adj_vertex(v2, create_adj_vertex(v1, weight)); 

       	if (newFlagv2) add_to_vertex_list(v1, v2); 	

	*vtxhead = v1;
	
	/* Uncomment this while testing to verify the addition of a vertex 
	 * printf("add_edge: Complete\n \n"); 
	 */
}

/* Adds the recently added vertex to the vertex list at the end of the vertex */ 
void add_to_vertex_list (vertex_t *v1, vertex_t *v2){

	if (v1->next == NULL) v1->next = v2; 
	else add_to_vertex_list(v1->next, v2); 

}

/* Searches the list to see if the vertex is already present.  
 * If the vertex is not found, allocate space. Else, return a pointer. */ 
void *find_or_create_vertex (vertex_t *v, char *name, int *flag){

	if (v == NULL){
		
		/* Uncomment this while testing to see if the vertex is not found in the list
		 * printf("find_or_create_vertex: Vertex '%s' not found\n", name);
		 */

		*flag = 1; 
		return alloc_vertex(name);
	}

	if (!strcmp(v->name, name)){
		
		/* Uncomment this while testing to see if the vertex is found in the list 
		 * printf("find_or_create_vertex: Found vertex '%s' \n", name); 
		 */

		return v; 
	} else return find_or_create_vertex(v->next, name, flag); 

}

/* Points the vertex portion of the adj_vertex_t to the vertex passed */ 
void *create_adj_vertex (vertex_t *v, int weight){

	adj_vertex_t *adj_v = (adj_vertex_t *)alloc_adj_vertex(weight); 

	if (v != NULL){
		adj_v->vertex = v; 
	}

	return adj_v; 
}

/* Adds an adjacent vertex to the end of the adjacent vertex list */ 
void add_adj_vertex (vertex_t *v, adj_vertex_t *adj_v){

	if (v->adj_list == NULL) v->adj_list = adj_v; 
	else add_to_adj_list(v->adj_list, adj_v); 

}

/* Finds the end of the adjacent vertex list and add the new adj_vertex_t */ 
void add_to_adj_list (adj_vertex_t *list, adj_vertex_t *newList){

	if (list->next == NULL) list->next = newList; 
	else add_to_adj_list(list->next, newList); 

}

/* Frees the vertex portion of the list after freeing each adjacent vertex list */ 
void free_list (vertex_t *vlist){
	
	vertex_t *tmp; 
	while (vlist != NULL){	
		if (vlist->adj_list != NULL) free_adj_list(vlist->adj_list); 
		tmp = vlist->next; 
		free(vlist); 
		vlist = tmp; 
	}

}

/* Frees the adjacent vertex list of a vertex */ 
void free_adj_list (adj_vertex_t *adj_vlist){

	adj_vertex_t *tmp; 
	while (adj_vlist != NULL){
		tmp = adj_vlist->next; 
		free(adj_vlist); 
		adj_vlist = tmp; 
	}

}

/* Finds a tour through the vertex list passed */ 
void find_tour(vertex_t *vlist){

	printf("\nTour path:\n"); 

	if (vlist == NULL) printf("  No tours found.\n"); 

	vertex_t *v, *vClean;  

	// Begin path trace 
	for (v = vlist; v != NULL; v = v->next){
		
		for (vClean = vlist; vClean != NULL; vClean = vClean->next) vClean->visited = 0; 
		
		char tour[300] = ""; 
		int weight = 0; 

		trace(v, tour, &weight); 

		if (all_visited(vlist)){
			printf("  %s", tour); 
			printf("\n\nTour length: %d\n\n", weight);
		       	break; 	
		}

	}

	if (!all_visited(vlist)) printf("  No tours found.\n"); 
}

/* Begins a trace through all the vertices, tagging each one that is visited while 
 * incrementing the total weight and adding the vertex name to the tour list. 
 * */ 
void trace(vertex_t *v, char *tour, int *weight){

	if (v != NULL && !(v->visited)) {
		adj_vertex_t *adj; 
		strcat(tour, v->name); 		
		strcat(tour, " ");
       	
		v->visited = 1;
		
		for (adj = v->adj_list; adj != NULL; adj=adj->next){
		
			if (!(adj->vertex->visited)){
				*weight += adj->edge_weight; 
				trace(adj->vertex, tour, weight);
			       	break; 	
			}

		}
	}
	
}

/* Checks the vertex list to make sure each has been visited. Returns 0 otherwise. */ 
int all_visited(vertex_t *vlist){

	vertex_t *v; 

	for (v = vlist; v != NULL; v = v->next){
		if (!(v->visited)) return 0; 
	}

	return 1; 

}
