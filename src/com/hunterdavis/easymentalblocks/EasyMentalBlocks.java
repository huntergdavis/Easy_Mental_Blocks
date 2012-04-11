package com.hunterdavis.easymentalblocks;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableRow.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.hunterdavis.easymentalblocks.ColorPickerDialog.OnColorChangedListener;

public class EasyMentalBlocks extends Activity {

	String pyramidName = null;
	String tempName = null;
	String tempBlockName = "";
	int color1 = 0;
	int color2 = 0;
	int color3 = 0;
	int colortop = 0;
	int color4 = 0;
	int color5 = 0;
	int color6 = 0;
	ImageView currentlySelectedImage = null;

	InventorySQLHelper pyramidData = new InventorySQLHelper(this);
	TextSQLHelper textData = new TextSQLHelper(this);
	ArrayAdapter<String> m_adapterForSpinner;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

		// delete button listener
		OnClickListener DeleteButtonListner = new OnClickListener() {
			public void onClick(View v) {
				yesnoDeleteHandler("Are you sure?",
						"Are you sure you want to delete?");
			}
		};
		Button deleteButton = (Button) findViewById(R.id.delete);
		deleteButton.setOnClickListener(DeleteButtonListner);

		// save button listener
		OnClickListener SaveButtonListner = new OnClickListener() {
			public void onClick(View v) {
				saveCurrentPyramid(v.getContext());
			}
		};
		Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(SaveButtonListner);

		// new button listener
		OnClickListener newButtonListner = new OnClickListener() {
			public void onClick(View v) {
				newPyramid();
			}
		};
		Button newPButton = (Button) findViewById(R.id.newrow);
		newPButton.setOnClickListener(newButtonListner);
		
		
		
		// dots button listener
		OnClickListener topDotsListner = new OnClickListener() {
			public void onClick(View v) {
				TextView topCheckBox = (TextView) findViewById(R.id.TopItemCheckBox);
				String blockname = topCheckBox.getText().toString();
				switchToTextView(v.getContext(),blockname);
			}
		};
		OnClickListener dotsOneListner = new OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) findViewById(R.id.itemOneCheckBox);
				String blockname = cb.getText().toString();
				switchToTextView(v.getContext(),blockname);
			}
		};
		OnClickListener dotsTwoListner = new OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) findViewById(R.id.itemTwoCheckBox);
				String blockname = cb.getText().toString();
				switchToTextView(v.getContext(),blockname);
			}
		};
		OnClickListener dotsThreeListner = new OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) findViewById(R.id.itemThreeCheckBox);
				String blockname = cb.getText().toString();
				switchToTextView(v.getContext(),blockname);
			}
		};
		OnClickListener dotsFourListner = new OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) findViewById(R.id.itemFourCheckBox);
				String blockname = cb.getText().toString();
				switchToTextView(v.getContext(),blockname);
			}
		};
		OnClickListener dotsFiveListner = new OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) findViewById(R.id.itemFiveCheckBox);
				String blockname = cb.getText().toString();
				switchToTextView(v.getContext(),blockname);
			}
		};
		OnClickListener dotsSixListner = new OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) findViewById(R.id.itemSixCheckBox);
				String blockname = cb.getText().toString();
				switchToTextView(v.getContext(),blockname);
			}
		};
		
		
		Button topDotsButton = (Button) findViewById(R.id.topdots);
		Button DotsOneButton = (Button) findViewById(R.id.dotsOne);
		Button DotsTwoButton = (Button) findViewById(R.id.dotsTwo);
		Button DotsThreeButton = (Button) findViewById(R.id.dotsThree);
		Button DotsFourButton = (Button) findViewById(R.id.dotsFour);
		Button DotsFiveButton = (Button) findViewById(R.id.dotsFive);
		Button DotsSixButton = (Button) findViewById(R.id.dotsSix);
		
		topDotsButton.setOnClickListener(topDotsListner);
		DotsOneButton.setOnClickListener(dotsOneListner);
		DotsTwoButton.setOnClickListener(dotsTwoListner);
		DotsThreeButton.setOnClickListener(dotsThreeListner);
		DotsFourButton.setOnClickListener(dotsFourListner);
		DotsFiveButton.setOnClickListener(dotsFiveListner);
		DotsSixButton.setOnClickListener(dotsSixListner);
		
		

		// image listeners
		// photo on click listener
		ImageView imageOne = (ImageView) findViewById(R.id.ImageViewOne);
		ImageView imageTwo = (ImageView) findViewById(R.id.ImageViewTwo);
		ImageView imageThree = (ImageView) findViewById(R.id.ImageViewThree);
		ImageView imageFour = (ImageView) findViewById(R.id.ImageViewFour);
		ImageView imageFive = (ImageView) findViewById(R.id.ImageViewFive);
		ImageView imageSix = (ImageView) findViewById(R.id.ImageViewSix);
		ImageView imageTop = (ImageView) findViewById(R.id.TopBlockImage);

		// Create an anonymous implementation of OnClickListener
		OnLongClickListener TopNameListner = new OnLongClickListener() {
			public boolean onLongClick(View v) {
				// do something when the button is clicked

				// in onCreate or any event where your want the user to

				AlertDialog.Builder alert = new AlertDialog.Builder(
						v.getContext());

				alert.setTitle("Block Name?");
				alert.setMessage("Please Enter A Name For The Block");

				// Set an EditText view to get user input
				final EditText input = new EditText(v.getContext());
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								tempName = input.getText().toString();

								Cursor tempCursor = getPyramidCursorByName(tempName);
								if (tempCursor.getCount() > 0) {
									Toast.makeText(getBaseContext(),
											"That Name is Already In Use!",
											Toast.LENGTH_LONG).show();
								} else {
									// Do something with value!
									if (tempName.length() > 1) {
										// select a file
										TextView topCheckBox = (TextView) findViewById(R.id.TopItemCheckBox);
										topCheckBox.setText(tempName);
									} else {
										Toast.makeText(getBaseContext(),
												"Invalid Name!",
												Toast.LENGTH_LONG).show();
									}
								}
							}

						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				alert.show();
				return true;

			}
		};
		TextView topCheckBox = (TextView) findViewById(R.id.TopItemCheckBox);
		topCheckBox.setOnLongClickListener(TopNameListner);

		// checkbox listeners
		// Create an anonymous implementation of OnClickListener
		OnLongClickListener otherNameListener = new OnLongClickListener() {
			public boolean onLongClick(final View v) {
				// do something when the button is clicked

				// in onCreate or any event where your want the user to

				AlertDialog.Builder alert = new AlertDialog.Builder(
						v.getContext());

				alert.setTitle("Block Name?");
				alert.setMessage("Please Enter A Name For The Block");

				// Set an EditText view to get user input
				final EditText input = new EditText(v.getContext());
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								tempName = input.getText().toString();

								// Do something with value!
								if (tempName.length() > 1) {
									// select a file
								} else {
									tempName = "Unnamed";
								}

								// set the correct view name
								final CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemOneCheckBox);
								final CheckBox checkBoxTwo = (CheckBox) findViewById(R.id.itemTwoCheckBox);
								final CheckBox checkBoxThree = (CheckBox) findViewById(R.id.itemThreeCheckBox);
								final CheckBox checkBoxFour = (CheckBox) findViewById(R.id.itemFourCheckBox);
								final CheckBox checkBoxFive = (CheckBox) findViewById(R.id.itemFiveCheckBox);
								final CheckBox checkBoxSix = (CheckBox) findViewById(R.id.itemSixCheckBox);

								if (tempName != null) {
									if (v.equals(checkBoxOne)) {
										checkBoxOne.setText(tempName);
									} else if (v.equals(checkBoxTwo)) {
										checkBoxTwo.setText(tempName);
									} else if (v.equals(checkBoxThree)) {
										checkBoxThree.setText(tempName);
									} else if (v.equals(checkBoxFour)) {
										checkBoxFour.setText(tempName);
									} else if (v.equals(checkBoxFive)) {
										checkBoxFive.setText(tempName);
									} else if (v.equals(checkBoxSix)) {
										checkBoxSix.setText(tempName);
									}
								}

							}

						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
								tempName = null;
							}
						});

				alert.show();

				return true;

			}
		};

		CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemOneCheckBox);
		checkBoxOne.setOnLongClickListener(otherNameListener);
		CheckBox checkBoxTwo = (CheckBox) findViewById(R.id.itemTwoCheckBox);
		checkBoxTwo.setOnLongClickListener(otherNameListener);
		CheckBox checkBoxThree = (CheckBox) findViewById(R.id.itemThreeCheckBox);
		checkBoxThree.setOnLongClickListener(otherNameListener);
		CheckBox checkBoxFour = (CheckBox) findViewById(R.id.itemFourCheckBox);
		checkBoxFour.setOnLongClickListener(otherNameListener);
		CheckBox checkBoxFive = (CheckBox) findViewById(R.id.itemFiveCheckBox);
		checkBoxFive.setOnLongClickListener(otherNameListener);
		CheckBox checkBoxSix = (CheckBox) findViewById(R.id.itemSixCheckBox);
		checkBoxSix.setOnLongClickListener(otherNameListener);

		checkBoxOne.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemOneCheckBox);
				if (checkBoxOne.isChecked()) {
					checkBoxOne.setPaintFlags(checkBoxOne.getPaintFlags()
							| Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					checkBoxOne.setPaintFlags(checkBoxOne.getPaintFlags()
							& ~Paint.STRIKE_THRU_TEXT_FLAG);
				}
			}
		});

		checkBoxTwo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxTwo = (CheckBox) findViewById(R.id.itemTwoCheckBox);
				if (checkBoxTwo.isChecked()) {
					checkBoxTwo.setPaintFlags(checkBoxTwo.getPaintFlags()
							| Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					checkBoxTwo.setPaintFlags(checkBoxTwo.getPaintFlags()
							& ~Paint.STRIKE_THRU_TEXT_FLAG);
				}

			}
		});

		checkBoxThree.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxThree = (CheckBox) findViewById(R.id.itemThreeCheckBox);
				if (checkBoxThree.isChecked()) {
					checkBoxThree.setPaintFlags(checkBoxThree.getPaintFlags()
							| Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					checkBoxThree.setPaintFlags(checkBoxThree.getPaintFlags()
							& ~Paint.STRIKE_THRU_TEXT_FLAG);
				}

			}
		});

		checkBoxFour.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxFour = (CheckBox) findViewById(R.id.itemFourCheckBox);
				if (checkBoxFour.isChecked()) {
					checkBoxFour.setPaintFlags(checkBoxFour.getPaintFlags()
							| Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					checkBoxFour.setPaintFlags(checkBoxFour.getPaintFlags()
							& ~Paint.STRIKE_THRU_TEXT_FLAG);
				}

			}
		});

		checkBoxFive.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxFive = (CheckBox) findViewById(R.id.itemFiveCheckBox);
				if (checkBoxFive.isChecked()) {
					checkBoxFive.setPaintFlags(checkBoxFive.getPaintFlags()
							| Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					checkBoxFive.setPaintFlags(checkBoxFive.getPaintFlags()
							& ~Paint.STRIKE_THRU_TEXT_FLAG);
				}

			}
		});

		checkBoxSix.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxSix = (CheckBox) findViewById(R.id.itemSixCheckBox);
				if (checkBoxSix.isChecked()) {
					checkBoxSix.setPaintFlags(checkBoxSix.getPaintFlags()
							| Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					checkBoxSix.setPaintFlags(checkBoxSix.getPaintFlags()
							& ~Paint.STRIKE_THRU_TEXT_FLAG);
				}

			}
		});

		// image onlongclick listeners
		imageOne.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView imageOne = (ImageView) findViewById(R.id.ImageViewOne);
				colorPicker(imageOne, v.getContext());
				return true;

			}
		});

		imageTwo.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView imageOne = (ImageView) findViewById(R.id.ImageViewTwo);
				colorPicker(imageOne, v.getContext());
				return true;
			}
		});

		imageThree.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView imageOne = (ImageView) findViewById(R.id.ImageViewThree);
				colorPicker(imageOne, v.getContext());
				return true;
			}
		});

		imageFour.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView imageOne = (ImageView) findViewById(R.id.ImageViewFour);
				colorPicker(imageOne, v.getContext());
				return true;
			}
		});

		imageFive.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView imageOne = (ImageView) findViewById(R.id.ImageViewFive);
				colorPicker(imageOne, v.getContext());
				return true;
			}
		});

		imageSix.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView imageOne = (ImageView) findViewById(R.id.ImageViewSix);
				colorPicker(imageOne, v.getContext());
				return true;
			}
		});

		imageTop.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				ImageView imageOne = (ImageView) findViewById(R.id.TopBlockImage);
				colorPicker(imageOne, v.getContext());
				return true;
			}
		});

		// set regular click listeners for the bottom 6 items
		imageOne.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemOneCheckBox);
				saveAndCreateNewFromName(v.getContext(), checkBoxOne.getText()
						.toString());
			}
		});

		// set regular click listeners for the bottom 6 items
		imageTwo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemTwoCheckBox);
				saveAndCreateNewFromName(v.getContext(), checkBoxOne.getText()
						.toString());
			}
		});

		// set regular click listeners for the bottom 6 items
		imageThree.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemThreeCheckBox);
				saveAndCreateNewFromName(v.getContext(), checkBoxOne.getText()
						.toString());
			}
		});

		// set regular click listeners for the bottom 6 items
		imageFour.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemFourCheckBox);
				saveAndCreateNewFromName(v.getContext(), checkBoxOne.getText()
						.toString());
			}
		});

		// set regular click listeners for the bottom 6 items
		imageFive.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemFiveCheckBox);
				saveAndCreateNewFromName(v.getContext(), checkBoxOne.getText()
						.toString());
			}
		});

		// set regular click listeners for the bottom 6 items
		imageSix.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemSixCheckBox);
				saveAndCreateNewFromName(v.getContext(), checkBoxOne.getText()
						.toString());
			}
		});

		fillImagesRandomly();

		// set an adapter for our spinner
		m_adapterForSpinner = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_spinner_item);
		m_adapterForSpinner
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner) findViewById(R.id.oldpyramids);
		spinner.setAdapter(m_adapterForSpinner);
		m_adapterForSpinner.add("*New Pyramid");

		spinner.setOnItemSelectedListener(new MyUnitsOnItemSelectedListener());

		// fill up our spinner item
		Cursor cursor = getPyramidssCursor();
		if (cursor.getCount() > 0) {
			deleteButton.setEnabled(true);
			while (cursor.moveToNext()) {
				String singlecardName = cursor.getString(1);
				m_adapterForSpinner.add(singlecardName);
			}
		} else {
			spinner.setEnabled(false);
		} 

	}

	
	public void switchToTextView(Context context,String blockname) {
		String blockLongText = getBlockLongText(blockname);
		
		if (blockname.equalsIgnoreCase("Hold To Edit")) {
			Toast.makeText(context, "Please Name The Block",
					Toast.LENGTH_LONG).show();
			return;
		}
		
		
		// do something when the button is clicked

		// in onCreate or any event where your want the user to
		tempBlockName = blockname;
		AlertDialog.Builder alert = new AlertDialog.Builder(
				context);

		alert.setTitle("Block Information?");
		alert.setMessage("Please Enter More Information For "+blockname);

		// Set an EditText view to get user input
		final EditText input = new EditText(context);
		input.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		input.setLines(10);
		input.setGravity(Gravity.TOP);
		input.setHorizontallyScrolling(false);
		input.setText(blockLongText);
		
		
		alert.setView(input);

		alert.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						String newtext = input.getText().toString();
						setBlockLongText(tempBlockName, newtext);
					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						
					}
				});

		alert.show();
		
		
		
	}
	
	public void saveAndCreateNewFromName(Context context, String name) {
		// test if our old one can be saved
		boolean didweSave = saveCurrentPyramid(context);

		if (didweSave == false) {
			return;
		}
		// test if new one exists, if does load it
		Boolean itExists = false;
		Cursor tempCursor = getPyramidCursorByName(name);
		if (tempCursor.getCount() > 0) {
			loadCardViewByName(name);
		} else {
			newPyramidByName(name);
		}

	}

	public void colorPicker(ImageView image, Context context) {
		// initialColor is the initially-selected color to be shown in the
		// rectangle on the left of the arrow.
		// for example, 0xff000000 is black, 0xff0000ff is blue. Please be aware
		// of the initial 0xff which is the alpha.

		currentlySelectedImage = image;
		OnColorChangedListener ourchangelistener = new OnColorChangedListener() {
			@Override
			public void colorChanged(int color) {
				// TODO Auto-generated method stub
				setColorGlobalFromImageView(currentlySelectedImage, color);
				genColor(currentlySelectedImage, color);
			}
		};
		new ColorPickerDialog(context, ourchangelistener, 333444).show();

	}

	// set up the listener class for spinner
	class MyUnitsOnItemSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			// Resources res = getResources();
			// updateSqlValues(rowId, "units", unitsarray[pos]);
			// set both global uri settings from the selected item using a sql
			// cursor
			if (pos > 0) {
				Spinner spinner = (Spinner) findViewById(R.id.oldpyramids);
				String spinnerText = spinner.getSelectedItem().toString();
				loadCardViewByName(spinnerText);
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {

		}
	}

	public void setColorGlobalFromImageView(ImageView image, int color) {
		ImageView imageOne = (ImageView) findViewById(R.id.ImageViewOne);
		ImageView imageTwo = (ImageView) findViewById(R.id.ImageViewTwo);
		ImageView imageThree = (ImageView) findViewById(R.id.ImageViewThree);
		ImageView imageFour = (ImageView) findViewById(R.id.ImageViewFour);
		ImageView imageFive = (ImageView) findViewById(R.id.ImageViewFive);
		ImageView imageSix = (ImageView) findViewById(R.id.ImageViewSix);
		ImageView imageTop = (ImageView) findViewById(R.id.TopBlockImage);

		if (imageOne.equals(image)) {
			color1 = color;
		} else if (imageTwo.equals(image)) {
			color2 = color;
		} else if (imageThree.equals(image)) {
			color3 = color;
		} else if (imageFour.equals(image)) {
			color4 = color;
		} else if (imageFive.equals(image)) {
			color5 = color;
		} else if (imageSix.equals(image)) {
			color6 = color;
		} else if (imageTop.equals(image)) {
			colortop = color;
		}

	}

	public void fillImagesRandomly() {
		ImageView imageOne = (ImageView) findViewById(R.id.ImageViewOne);
		ImageView imageTwo = (ImageView) findViewById(R.id.ImageViewTwo);
		ImageView imageThree = (ImageView) findViewById(R.id.ImageViewThree);
		ImageView imageFour = (ImageView) findViewById(R.id.ImageViewFour);
		ImageView imageFive = (ImageView) findViewById(R.id.ImageViewFive);
		ImageView imageSix = (ImageView) findViewById(R.id.ImageViewSix);
		ImageView imageTop = (ImageView) findViewById(R.id.TopBlockImage);

		color1 = randomFromRange(0, Integer.MAX_VALUE);
		color2 = randomFromRange(0, Integer.MAX_VALUE);
		color3 = randomFromRange(0, Integer.MAX_VALUE);
		colortop = randomFromRange(0, Integer.MAX_VALUE);
		color4 = randomFromRange(0, Integer.MAX_VALUE);
		color5 = randomFromRange(0, Integer.MAX_VALUE);
		color6 = randomFromRange(0, Integer.MAX_VALUE);

		genColor(imageOne, color1);
		genColor(imageTwo, color2);
		genColor(imageThree, color3);
		genColor(imageFour, color4);
		genColor(imageFive, color5);
		genColor(imageSix, color6);
		genColor(imageTop, colortop);

	}

	protected void yesnoDeleteHandler(String title, String mymessage) {
		new AlertDialog.Builder(this)
				.setMessage(mymessage)
				.setTitle(title)
				.setCancelable(true)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								DeletePyramidByName(pyramidName);

							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();
	}

	private Cursor getPyramidssCursor() {
		SQLiteDatabase db = pyramidData.getReadableDatabase();
		Cursor cursor = db.query(InventorySQLHelper.TABLE, null, null, null,
				null, null, null);
		startManagingCursor(cursor);
		return cursor;
	}

	private Cursor getPyramidCursorByName(String rowId) {
		SQLiteDatabase db = pyramidData.getReadableDatabase();
		Cursor cursor = db.query(InventorySQLHelper.TABLE, null, "topblock = '"
				+ rowId + "'", null, null, null, null);
		startManagingCursor(cursor);
		return cursor;
	}
	
	private void updateDatabaseName(String oldname, String newname) {
		SQLiteDatabase db = pyramidData.getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(InventorySQLHelper.TOPBLOCK, newname);
		String strFilter = " topblock='" + oldname + "'";
		db.update(InventorySQLHelper.TABLE, args, strFilter, null);
	}

	
	private void setBlockLongText(String blockName, String blockLongText) {
		SQLiteDatabase db = textData.getWritableDatabase();
		ContentValues args = new ContentValues();
		args.put(TextSQLHelper.DATA, blockLongText);
		String strFilter = " block='" + blockName + "'";
		int retval = db.update(TextSQLHelper.TABLE, args, strFilter, null);
		if(retval == 0) {
			args.put(TextSQLHelper.BLOCK, blockName);
			long retret = db.insert(TextSQLHelper.TABLE,null, args);
		}
	}
	
	private String getBlockLongText(String blockName) {
		SQLiteDatabase db = textData.getReadableDatabase();
		Cursor cursor = db.query(TextSQLHelper.TABLE, null, "block= '"
				+ blockName + "'", null, null, null, null);
		startManagingCursor(cursor);
		if (cursor.moveToFirst() == true) {
			return cursor.getString(2);
		}
		else
			return "";
		
	}
	
	
	public void DeletePyramidByName(String card) {
		if (card.equals("Hold To Edit")) {
			newPyramid();
			return;
		}
		SQLiteDatabase db = pyramidData.getWritableDatabase();
		db.delete(InventorySQLHelper.TABLE, "topblock = '" + card + "'", null);
		db.close();

		m_adapterForSpinner.remove(card);

		Spinner spinner = (Spinner) findViewById(R.id.oldpyramids);
		if (spinner.getCount() == 0) {
			spinner.setEnabled(false);
			Button deleteButton = (Button) findViewById(R.id.delete);
			deleteButton.setEnabled(false);
			newPyramid();
		} else {
			int spinnerSelected = spinner.getSelectedItemPosition() - 1;
			String SpinnerText = m_adapterForSpinner.getItem(spinnerSelected);
			loadCardViewByName(SpinnerText);
		}

	}

	public void loadCardViewByName(String name) {
		if (name.equals("*New Pyramid")) {
			newPyramid();
			return;
		}

		Cursor cursor = getPyramidCursorByName(name);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
		} else {
			cursor.close();
			return;
		}

		// get the views
		final CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemOneCheckBox);
		final CheckBox checkBoxTwo = (CheckBox) findViewById(R.id.itemTwoCheckBox);
		final CheckBox checkBoxThree = (CheckBox) findViewById(R.id.itemThreeCheckBox);
		final CheckBox checkBoxFour = (CheckBox) findViewById(R.id.itemFourCheckBox);
		final CheckBox checkBoxFive = (CheckBox) findViewById(R.id.itemFiveCheckBox);
		final CheckBox checkBoxSix = (CheckBox) findViewById(R.id.itemSixCheckBox);

		checkBoxOne.setPaintFlags(checkBoxOne.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxTwo.setPaintFlags(checkBoxTwo.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxThree.setPaintFlags(checkBoxThree.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxFour.setPaintFlags(checkBoxFour.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxFive.setPaintFlags(checkBoxFive.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxSix.setPaintFlags(checkBoxSix.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);

		String nameOne = cursor.getString(2);
		String nameTwo = cursor.getString(3);
		String nameThree = cursor.getString(4);
		String nameFour = cursor.getString(5);
		String nameFive = cursor.getString(6);
		String nameSix = cursor.getString(7);

		checkBoxOne.setText(nameOne);
		checkBoxTwo.setText(nameTwo);
		checkBoxThree.setText(nameThree);
		checkBoxFour.setText(nameFour);
		checkBoxFive.setText(nameFive);
		checkBoxSix.setText(nameSix);

		int checkOne = cursor.getInt(8);
		int checkTwo = cursor.getInt(9);
		int checkThree = cursor.getInt(10);
		int checkFour = cursor.getInt(11);
		int checkFive = cursor.getInt(12);
		int checkSix = cursor.getInt(13);

		if (checkOne > 0) {
			checkBoxOne.setChecked(true);
			checkBoxOne.setPaintFlags(checkBoxOne.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			checkBoxOne.setChecked(false);
		}
		if (checkTwo > 0) {
			checkBoxTwo.setChecked(true);
			checkBoxTwo.setPaintFlags(checkBoxTwo.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			checkBoxTwo.setChecked(false);
		}
		if (checkThree > 0) {
			checkBoxThree.setChecked(true);
			checkBoxThree.setPaintFlags(checkBoxThree.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			checkBoxThree.setChecked(false);
		}
		if (checkFour > 0) {
			checkBoxFour.setChecked(true);
			checkBoxFour.setPaintFlags(checkBoxFour.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			checkBoxFour.setChecked(false);
		}
		if (checkFive > 0) {
			checkBoxFive.setChecked(true);
			checkBoxFive.setPaintFlags(checkBoxFive.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			checkBoxFive.setChecked(false);
		}
		if (checkSix > 0) {
			checkBoxSix.setChecked(true);
			checkBoxSix.setPaintFlags(checkBoxSix.getPaintFlags()
					| Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			checkBoxSix.setChecked(false);
		}

		int colorOne = cursor.getInt(14);
		int colorTwo = cursor.getInt(15);
		int colorThree = cursor.getInt(16);
		int colorFour = cursor.getInt(17);
		int colorFive = cursor.getInt(18);
		int colorSix = cursor.getInt(19);
		int colorTop = cursor.getInt(20);

		ImageView imageOne = (ImageView) findViewById(R.id.ImageViewOne);
		ImageView imageTwo = (ImageView) findViewById(R.id.ImageViewTwo);
		ImageView imageThree = (ImageView) findViewById(R.id.ImageViewThree);
		ImageView imageFour = (ImageView) findViewById(R.id.ImageViewFour);
		ImageView imageFive = (ImageView) findViewById(R.id.ImageViewFive);
		ImageView imageSix = (ImageView) findViewById(R.id.ImageViewSix);
		ImageView imageTop = (ImageView) findViewById(R.id.TopBlockImage);

		genColor(imageOne, colorOne);
		genColor(imageTwo, colorTwo);
		genColor(imageThree, colorThree);
		genColor(imageFour, colorFour);
		genColor(imageFive, colorFive);
		genColor(imageSix, colorSix);
		genColor(imageTop, colorTop);

		// get the top name
		final TextView myTopName = (TextView) findViewById(R.id.TopItemCheckBox);
		myTopName.setText(name);

		pyramidName = name;

	}

	public void newPyramidByName(String name) {

		Spinner spinner = (Spinner) findViewById(R.id.oldpyramids);
		spinner.setSelection(0);

		final CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemOneCheckBox);
		final CheckBox checkBoxTwo = (CheckBox) findViewById(R.id.itemTwoCheckBox);
		final CheckBox checkBoxThree = (CheckBox) findViewById(R.id.itemThreeCheckBox);
		final CheckBox checkBoxFour = (CheckBox) findViewById(R.id.itemFourCheckBox);
		final CheckBox checkBoxFive = (CheckBox) findViewById(R.id.itemFiveCheckBox);
		final CheckBox checkBoxSix = (CheckBox) findViewById(R.id.itemSixCheckBox);

		String localname = "Hold To Edit";
		pyramidName = name;

		checkBoxOne.setText(localname);
		checkBoxTwo.setText(localname);
		checkBoxThree.setText(localname);
		checkBoxFour.setText(localname);
		checkBoxFive.setText(localname);
		checkBoxSix.setText(localname);

		checkBoxOne.setChecked(false);
		checkBoxTwo.setChecked(false);
		checkBoxThree.setChecked(false);
		checkBoxFour.setChecked(false);
		checkBoxFive.setChecked(false);
		checkBoxSix.setChecked(false);

		checkBoxOne.setPaintFlags(checkBoxOne.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxTwo.setPaintFlags(checkBoxTwo.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxThree.setPaintFlags(checkBoxThree.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxFour.setPaintFlags(checkBoxFour.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxFive.setPaintFlags(checkBoxFive.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);
		checkBoxSix.setPaintFlags(checkBoxSix.getPaintFlags()
				& ~Paint.STRIKE_THRU_TEXT_FLAG);

		fillImagesRandomly();

		final TextView myTopName = (TextView) findViewById(R.id.TopItemCheckBox);
		myTopName.setText(name);

	}

	public void newPyramid() {
		newPyramidByName("Hold To Edit");

	}

	public boolean saveCurrentPyramid(Context context) {

		// get the views
		final CheckBox checkBoxOne = (CheckBox) findViewById(R.id.itemOneCheckBox);
		final CheckBox checkBoxTwo = (CheckBox) findViewById(R.id.itemTwoCheckBox);
		final CheckBox checkBoxThree = (CheckBox) findViewById(R.id.itemThreeCheckBox);
		final CheckBox checkBoxFour = (CheckBox) findViewById(R.id.itemFourCheckBox);
		final CheckBox checkBoxFive = (CheckBox) findViewById(R.id.itemFiveCheckBox);
		final CheckBox checkBoxSix = (CheckBox) findViewById(R.id.itemSixCheckBox);

		String nameOne = (String) checkBoxOne.getText();
		String nameTwo = (String) checkBoxTwo.getText();
		String nameThree = (String) checkBoxThree.getText();
		String nameFour = (String) checkBoxFour.getText();
		String nameFive = (String) checkBoxFive.getText();
		String nameSix = (String) checkBoxSix.getText();

		int checkOne = 0;
		int checkTwo = 0;
		int checkThree = 0;
		int checkFour = 0;
		int checkFive = 0;
		int checkSix = 0;

		if (checkBoxOne.isChecked()) {
			checkOne = 1;
		}
		if (checkBoxTwo.isChecked()) {
			checkTwo = 1;
		}
		if (checkBoxThree.isChecked()) {
			checkThree = 1;
		}
		if (checkBoxFour.isChecked()) {
			checkFour = 1;
		}
		if (checkBoxFive.isChecked()) {
			checkFive = 1;
		}
		if (checkBoxSix.isChecked()) {
			checkSix = 1;
		}

		// get the top name
		final TextView myTopName = (TextView) findViewById(R.id.TopItemCheckBox);
		String topTextName = (String) myTopName.getText();

		if (topTextName.equalsIgnoreCase("Hold To Edit")) {
			Toast.makeText(context, "Please Name The Top Block",
					Toast.LENGTH_LONG).show();
			return false;
		}

		Boolean updateIt = false;
		Cursor tempCursor = getPyramidCursorByName(topTextName);
		if (tempCursor.getCount() > 0) {
			updateIt = true;
		}

		SQLiteDatabase db = pyramidData.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(InventorySQLHelper.TOPBLOCK, topTextName);

		values.put(InventorySQLHelper.BLOCKONE, nameOne);
		values.put(InventorySQLHelper.BLOCKTWO, nameTwo);
		values.put(InventorySQLHelper.BLOCKTHREE, nameThree);
		values.put(InventorySQLHelper.BLOCKFOUR, nameFour);
		values.put(InventorySQLHelper.BLOCKFIVE, nameFive);
		values.put(InventorySQLHelper.BLOCKSIX, nameSix);

		values.put(InventorySQLHelper.CHECKEDONE, checkOne);
		values.put(InventorySQLHelper.CHECKEDTWO, checkTwo);
		values.put(InventorySQLHelper.CHECKEDTHREE, checkThree);
		values.put(InventorySQLHelper.CHECKEDFOUR, checkFour);
		values.put(InventorySQLHelper.CHECKEDFIVE, checkFive);
		values.put(InventorySQLHelper.CHECKEDSIX, checkSix);

		values.put(InventorySQLHelper.COLORTOP, colortop);

		values.put(InventorySQLHelper.COLORONE, color1);
		values.put(InventorySQLHelper.COLORTWO, color2);
		values.put(InventorySQLHelper.COLORTHREE, color3);
		values.put(InventorySQLHelper.COLORFOUR, color4);
		values.put(InventorySQLHelper.COLORFIVE, color5);
		values.put(InventorySQLHelper.COLORSIX, color6);

		Spinner spinner = (Spinner) findViewById(R.id.oldpyramids);

		if (updateIt == true) {
			long latestRowId = db.update(InventorySQLHelper.TABLE, values,
					"topblock = '" + topTextName + "'", null);
		} else {
			long latestRowId = db
					.insert(InventorySQLHelper.TABLE, null, values);
			m_adapterForSpinner.add(topTextName);
			spinner.setSelection(spinner.getCount() - 1);
		}
		db.close();

		spinner.setEnabled(true);

		Button deleteButton = (Button) findViewById(R.id.delete);
		deleteButton.setEnabled(true);
		return true;
	}

	public Boolean genColor(ImageView imgview, int Color) {

		// create a width*height long int array and populate it with random 1 or
		// 0
		// final Random myRandom = new Random();
		int rgbSize = 50 * 50;
		int[] rgbValues = new int[rgbSize];
		for (int i = 0; i < rgbSize; i++) {
			rgbValues[i] = Color;
		}

		// create a width*height bitmap
		BitmapFactory.Options staticOptions = new BitmapFactory.Options();
		staticOptions.inSampleSize = 2;
		Bitmap staticBitmap = Bitmap.createBitmap(rgbValues, 50, 50,
				Bitmap.Config.RGB_565);

		// set the imageview to the static
		imgview.setImageBitmap(staticBitmap);

		return true;

	}

	int randomFromRange(int low, int high) {
		if (low == high) {
			return low;
		}
		final Random myRandom = new Random();
		Float myNewRandomPercent = myRandom.nextFloat();
		Integer actualResult = (int) Math.round(low
				+ (Math.abs((high - low)) * (myNewRandomPercent)));
		return actualResult;
	}

}