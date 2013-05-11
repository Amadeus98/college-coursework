/* forward declarations so self-referential structures are simpler */
typedef struct vertex vertex_t;
typedef struct adj_vertex adj_vertex_t;

struct vertex {
	char *name;
	adj_vertex_t *adj_list;
	vertex_t *next;
	int visited; 
};

struct adj_vertex {
	vertex_t *vertex;
	int edge_weight;
	adj_vertex_t *next;	
};


void *alloc_vertex (char *name); 
void *alloc_adj_vertext (int edge_weight); 

void add_edge (vertex_t **vtxhead, char *v1_name, char *v2_name, int weight);
void add_to_vertex_list (vertex_t *v1, vertex_t *v2); 

void *find_or_create_vertex (vertex_t *v, char *name, int *flag); 
void *create_adj_vertex (vertex_t *v, int weight);

void add_adj_vertex (vertex_t *v, adj_vertex_t *adj_v); 
void add_to_adj_list (adj_vertex_t *list, adj_vertex_t *newList);

void free_list (vertex_t *vlist); 
void free_adj_list (adj_vertex_t *adj_vlist); 

void find_tour (vertex_t *vlist);
void trace(vertex_t *v, char *tour, int *weight);
int all_visited(vertex_t *vlist); 
