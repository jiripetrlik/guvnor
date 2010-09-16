/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.guvnor.client.modeldriven.ui;

import org.drools.guvnor.client.common.ClickableLabel;
import org.drools.guvnor.client.common.DirtyableFlexTable;
import org.drools.guvnor.client.common.DirtyableHorizontalPane;
import org.drools.guvnor.client.common.FormStylePopup;
import org.drools.guvnor.client.common.ImageButton;
import org.drools.guvnor.client.messages.Constants;
import org.drools.guvnor.client.modeldriven.HumanReadable;
import org.drools.ide.common.client.modeldriven.SuggestionCompletionEngine;
import org.drools.ide.common.client.modeldriven.brl.FactPattern;
import org.drools.ide.common.client.modeldriven.brl.FromCompositeFactPattern;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author esteban
 */
public class FromCompositeFactPatternWidget extends RuleModellerWidget {

    protected FromCompositeFactPattern pattern;
    protected DirtyableFlexTable layout;
    protected Constants constants = ((Constants) GWT.create(Constants.class));
    protected boolean readOnly;

    private FactPatternWidget factPatternWidget;
    private ExpressionBuilder expressionBuilder;

    public FromCompositeFactPatternWidget(RuleModeller modeller,
            FromCompositeFactPattern pattern) {
        this(modeller, pattern, null);
    }

    public FromCompositeFactPatternWidget(RuleModeller modeller,
            FromCompositeFactPattern pattern, Boolean readOnly) {
        super(modeller);
        this.pattern = pattern;

        //if readOnly is null, the readOnly attribute is calculated.
        if (readOnly == null) {
            this.calculateReadOnly();
        }else{
            this.readOnly = readOnly;
        }


        this.layout = new DirtyableFlexTable();
        if (this.readOnly) {
            this.layout.addStyleName("editor-disabled-widget");
        }
        this.layout.addStyleName("model-builderInner-Background");

        doLayout();
        initWidget(layout);
    }

    protected void doLayout() {

        int r = 0;

        if (pattern.getFactPattern() != null) {
            FactPattern fact = pattern.getFactPattern();
            if (fact != null) {


                if (this.readOnly) {
                    //creates a new read-only FactPatternWidget
                    this.factPatternWidget = new FactPatternWidget(this.getModeller(), fact, false, true);
                    this.layout.setWidget(r,
                            0, factPatternWidget);
                } else {
                    this.factPatternWidget = new FactPatternWidget(this.getModeller(), fact, true,false);
                    this.factPatternWidget.addOnModifiedCommand(new Command() {
                        public void execute() {
                            setModified(true);
                        }
                    });
//                    this.layout.setWidget(r,
//                            0,
//                            addRemoveButton(factPatternWidget, new ClickListener() {
//
//                        public void onClick(Widget w) {
//                            if (Window.confirm(constants.RemoveThisEntireConditionQ())) {
//                                setModified(true);
//                                pattern.setFactPattern(null);
//                                getModeller().refreshWidget();
//                            }
//                        }
//                    }));
					this.layout.setWidget(
							r,
							0,
							addRemoveButton(factPatternWidget,
									new ClickHandler() {

										public void onClick(ClickEvent event) {
											if (Window.confirm(constants
													.RemoveThisEntireConditionQ())) {
												setModified(true);
												pattern.setFactPattern(null);
												getModeller().refreshWidget();
											}

										}
									}));
                }
                r++;
            }
        }

        this.layout.setWidget(r,
                0,
                getCompositeLabel());

    }

    protected Widget getCompositeLabel() {

    	ClickHandler click = new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				Widget w = (Widget)event.getSource();
				showFactTypeSelector(w);
				
			}
		};
        String lbl = "<div class='x-form-field'>" + HumanReadable.getCEDisplayName("from") + "</div>";

        DirtyableFlexTable panel = new DirtyableFlexTable();

        int r = 0;

        if (pattern.getFactPattern() == null) {
            panel.setWidget(r, 0, new ClickableLabel("<br> <font color='red'>" + constants.clickToAddPatterns() + "</font>", click, !this.readOnly));
            r++;
        }


        panel.setWidget(r, 0, new HTML(lbl));
        this.expressionBuilder =new ExpressionBuilder(this.getModeller(), this.pattern.getExpression(), this.readOnly);
        this.expressionBuilder.addOnModifiedCommand(new Command() {
            public void execute() {
                setModified(true);
            }
        });
        panel.setWidget(r, 1, this.expressionBuilder);

        return panel;
    }

    /**
     * Pops up the fact selector.
     */
    protected void showFactTypeSelector(final Widget w) {
        SuggestionCompletionEngine completions = this.getModeller().getSuggestionCompletions();
        final ListBox box = new ListBox();
        String[] facts = completions.getFactTypes();

        box.addItem(constants.Choose());

        for (int i = 0; i < facts.length; i++) {
            box.addItem(facts[i]);
        }
        box.setSelectedIndex(0);

        final FormStylePopup popup = new FormStylePopup();
        popup.setTitle(constants.NewFactPattern());
        popup.addAttribute(constants.chooseFactType(),
                box);
        box.addChangeHandler(new ChangeHandler() {
			
			public void onChange(ChangeEvent event) {
                pattern.setFactPattern(new FactPattern(box.getItemText(box.getSelectedIndex())));
                setModified(true);
                getModeller().refreshWidget();
                popup.hide();
			}
		});


        popup.show();
    }

    protected Widget addRemoveButton(Widget w, ClickHandler listener) {
        DirtyableHorizontalPane horiz = new DirtyableHorizontalPane();

        final Image remove = new ImageButton("images/delete_item_small.gif"); //NON-NLS
        remove.setTitle(constants.RemoveThisBlockOfData());
        remove.addClickHandler(listener);
        //remove.addClickListener(listener);


        horiz.setWidth("100%");
        w.setWidth("100%");

        horiz.add(w);
        if (!this.readOnly) {
            horiz.add(remove);
        }
        return horiz;
    }

    public boolean isDirty() {
        return layout.hasDirty();
    }

    protected void calculateReadOnly() {
        if (this.pattern.factPattern != null) {
            this.readOnly = !this.getModeller().getSuggestionCompletions().containsFactType(this.pattern.factPattern.factType);
        }
    }

    @Override
    public boolean isReadOnly() {
        return this.readOnly;
    }
}
