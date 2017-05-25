package com.project.unice.embeddedprogproject;

import android.view.View;

/**
 * Interface for a custom view.
 */
public interface ViewHolder {
    /**
     * Inflate the component of the view.
     * @param view the view to initialize
     * @param position the position of the component in the list
     * @return true if no problem (for test purpose)
     */
    boolean initializeView(View view, int position);

    /**
     * Set the values of the component.
     * @param position the position of the component in the list
     * @return true if no problem (for test purpose)
     */
    boolean fillView(int position);
}
