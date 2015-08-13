/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.util;

/**
 *
 * @author fchaves
 */
abstract public class Maybe<A> {
    private Maybe() {}
    
    abstract public boolean isNothing();
    abstract public boolean isJust();
    abstract public A getValue();
    
    static private Nothing<?> nothing = new Nothing<>();
    
    static public <A> Maybe<A> getNothing() {
        @SuppressWarnings("unchecked")
        Nothing<A> result = (Nothing<A>)nothing;
        return result;
    }

    static public <A> Maybe<A> Just(A value) {
        return new Just(value);
    }

    static private class Nothing<A> extends Maybe<A> {
        private Nothing() { super(); }

        @Override
        public boolean isNothing() {
            return true;
        }

        @Override
        public boolean isJust() {
            return false;
        }

        @Override
        public A getValue() {
            throw new UnsupportedOperationException("Nothing doesn't has a value.");
        }

        @Override
        public String toString() {
            return "Nothing" ;
        }
        
        
    }
    
    static private class Just<A> extends Maybe<A> {
        private A value;
        
        private Just(A value) {
            this.value = value;
        }

        @Override
        public boolean isNothing() {
            return false;
        }

        @Override
        public boolean isJust() {
            return true;
        }

        @Override
        public A getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Just("+value.toString()+")";
        }
        
        
    }
}
