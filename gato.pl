%Escoje numero en base a características
posicion(X):-random(1,10,X).
lineas([[A,B,C],[D,E,F],[G,H,I]],
       [[A,B,C],[D,E,F],[G,H,I],[A,D,G],[B,E,H],[C,F,I],[A,E,I],[G,E,C]]).

ficha(o).
ficha(x).

movimiento(T,X) :-
   lineas(T,L),
   member([G,G,X],L),
   not(ficha(X)).

movimiento(T,X) :-
   lineas(T,L),
   member([G,X,G],L),
   not(ficha(X)).

movimiento(T,X) :-
   lineas(T,L),
   member([X,G,G],L),
   not(ficha(X)).

jugada(T,X):- movimiento(T,X).
jugada(T,X):- not(movimiento(T,X)),posicion(X).
