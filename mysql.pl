abrir:-odbc_connect('prolog',_,
                   [user(root),
                    password(''),
                    alias(prolog),
                    open(once)]).

cerrar:-odbc_disconnect('prolog').

insertar(R):-odbc_query('prolog', R).
insertar(X,Y,R):-concat('INSERT INTO jugadores (ID, Nombre, Victorias, Derrotas, Empates) VALUES (',X,Z),
concat(Z,',\'',M), concat(M,Y,N), concat(N,'\',0,0,0);',R),
insertar(R).

actualiza_datos(N,D,C):-cadena_actualiza_datos(N,D,C,R), odbc_query('prolog',
			R).
cadena_actualiza_datos(N,D,C,R):-concat('UPDATE jugadores SET ',D,Z), concat(Z,'=',M), concat(M,C,P), concat(P,' WHERE nombre =\'',K), concat(K,N,J), concat(J,'\'',R).

borra(F):-odbc_query('prolog','DELETE alumno where id_alumno=1',affected(F)).

leer_nombres(F):-odbc_query('prolog','SELECT nombre FROM jugadores',row(F)).
leer_id(F):-odbc_query('prolog',
		      'SELECT id FROM jugadores ORDER BY id DESC LIMIT 1',
		     row(F)).

leer_datos(N,V,D,E):-cadena_leer_datos(N,R), odbc_query('prolog',
			     R,
			     row(V,D,E)).
cadena_leer_datos(X,R):-concat('SELECT victorias, derrotas, empates FROM jugadores WHERE nombre=\'',X,M), concat(M,'\'',R).

leer_todos(F):-odbc_query('prolog','SELECT * FROM alumno',F).
