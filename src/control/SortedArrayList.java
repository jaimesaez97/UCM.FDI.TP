package control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class SortedArrayList<E> extends ArrayList<E> {
	
	private static final long serialVersionUID = 1L;
		private Comparator<E> _cmp;
		 public SortedArrayList(Comparator<E> cmp) {
			 this.set_cmp(cmp);
		 };
		 
		 @Override
		 public boolean add(E e) {
			 int i = 0;
			 boolean ret = false;
			 
			 if(this.size() == 0) {
				 this.add(0, e);
				 return true;
			 }
			 
			 while( i < this.size()) {
				if( (this._cmp.compare(this.get(i), e)) == 1){
					this.add(i, e);
					return true;
				}
				++i;
			 }	
			 
			if((this._cmp.compare(this.get(i - 1), e) ==  0 )|| (this._cmp.compare(this.get(i - 1), e) == -1)){ // SI YA HEMOS ACABADO Y EL ULTIMO
			 	//ERA MENOR O IGUAL INSERTO AL FINAL
			 	this.add(i, e);
				return true;
			}
			
			 
			return ret; 
		}
		 public boolean addAll(Collection<? extends E> c) {
			 
			 boolean ret = true;
			 Iterator<? extends E> iter = c.iterator();
			 
			 while (iter.hasNext())
			 { 
			     E item1 = iter.next();
			   if(!this.add(item1))
				   ret = false;
			    
			 }
			return ret;
			 
		 }
		 // sobreescribir los m�todos que realizan operaciones de
		 // inserci�n basados en un �ndice para que lancen excepcion.
		 // Ten en cuenta que esta operaci�n romper�a la ordenaci�n.
		 // estos m�todos son add(int index, E element),
		 // addAll(int index, Colection<? Extends E>) y E set(int index, E element).
		public Comparator<E> get_cmp() {
			return _cmp;
		}
		public void set_cmp(Comparator<E> _cmp) {
			this._cmp = _cmp;
		}
		
}
