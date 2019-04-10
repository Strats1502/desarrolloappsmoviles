package itlapps.juanjoseesva.contacts.activities

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import itlapps.juanjoseesva.contacts.R
import itlapps.juanjoseesva.contacts.model.Contact
import kotlinx.android.synthetic.main.activity_addcontact.*
import kotlinx.android.synthetic.main.menu_bottom_addphoto.*
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest

class AddContactActivity : AppCompatActivity() {
    var realm: Realm = Realm.getDefaultInstance()
    private val GALLERY = 1
    private val CAMERA = 2
    private val CAMERA_REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcontact)

        Realm.init(this)

        setAdapters()

        iv_photo.setOnClickListener {
            selectPhoto()
        }


        et_birthday.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selectDate()
            }
        }

        et_birthday.setOnClickListener {
            selectDate()
        }

        button_save.setOnClickListener {
            saveContact()
        }
    }

    private fun setAdapters() {
        val context: Context = this
        val spinnerItem: Int = android.R.layout.simple_spinner_item
        val spinnerDropdownItem: Int = android.R.layout.simple_spinner_dropdown_item

        val adapterAreas: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context,
            R.array.areas, spinnerItem
        )
        adapterAreas.setDropDownViewResource(spinnerDropdownItem)

        val adapterJobs: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context,
            R.array.jobs, spinnerItem
        )
        adapterJobs.setDropDownViewResource(spinnerDropdownItem)

        val adapterScholarship: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context,
            R.array.schooling, spinnerItem
        )
        adapterScholarship.setDropDownViewResource(spinnerDropdownItem)

        val adapterNationality: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context,
            R.array.nationalities, spinnerItem
        )
        adapterNationality.setDropDownViewResource(spinnerDropdownItem)

        val adapterStatuses: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context,
            R.array.statuses, spinnerItem
        )
        adapterStatuses.setDropDownViewResource(spinnerDropdownItem)

        spinner_area.adapter = adapterAreas
        spinner_job.adapter = adapterJobs
        spinner_scholarship.adapter = adapterScholarship
        spinner_nationality.adapter = adapterNationality
        spinner_status.adapter = adapterStatuses
    }

    fun selectPhoto() {
        val bottomMenu = BottomSheetDialog(this)
        bottomMenu.setContentView(R.layout.menu_bottom_addphoto)

        bottomMenu.ll_camera.setOnClickListener {
            openCamera()
            bottomMenu.dismiss()
        }

        bottomMenu.ll_gallery.setOnClickListener {
            openGallery()
            bottomMenu.dismiss()
        }

        bottomMenu.show()
    }

    fun selectDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            //lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)
            var day = if (dayOfMonth < 10) "0${dayOfMonth}" else dayOfMonth
            var month = if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else monthOfYear + 1
            et_birthday.setText("${day}/${month}/${year}")
        }, year, month, day)

        dpd.show()
    }

    fun saveContact() {
        if (validate()) {
            val name: String = et_name.text.toString()
            val lastName1: String = et_lastname1.text.toString()
            val lastName2: String = et_lastname2.text.toString()
            val birthday: String = et_birthday.text.toString()
            val phone: String = et_phone.text.toString()
            val email: String = et_email.text.toString()
            val address: String = et_address.text.toString()
            val paysheet: String = et_paysheet.text.toString()
            val area: String = spinner_area.selectedItem.toString()
            val job: String = spinner_job.selectedItem.toString()
            val state: String = et_state.text.toString()
            val nss: String = et_nss.text.toString()
            val scholarship: String = spinner_scholarship.selectedItem.toString()
            val nationality: String = spinner_nationality.selectedItem.toString()
            val status: String = spinner_status.selectedItem.toString()
            val diseases: String = et_diseases.text.toString()
            val emergencyContactName: String = et_emergencycontactname.text.toString()
            val emergencyContactPhone: String = et_emergencycontactphone.text.toString()

            try {
                realm.beginTransaction()
                val contact = realm.createObject(Contact::class.java, paysheet)
                contact.name = name
                contact.lastName1 = lastName1
                contact.lastName2 = lastName2
                contact.birthday = birthday
                contact.phone = phone
                contact.email = email
                contact.address = address
                //contact.paysheet = paysheet
                contact.area = area
                contact.job = job
                contact.state = state
                contact.nss = nss
                contact.scholarship = scholarship
                contact.nationality = nationality
                contact.status = status
                contact.photoURL = getBase64Image()
                contact.diseases = diseases
                contact.emergencyContactName = emergencyContactName
                contact.emergencyContactPhone = emergencyContactPhone
                realm.commitTransaction()

                this.finish()
            } catch (exception: RealmPrimaryKeyConstraintException) {
                realm.cancelTransaction()
                et_paysheet.setError(getString(R.string.inputtext_error_paysheetalreadyexists))
                et_paysheet.requestFocus()
            }

        }
    }

    //Validate all edit text and return true if all are correct
    private fun validate(): Boolean {
        val errorEmpty = getString(R.string.inputtext_error_empty)
        val errorRegex = getString(R.string.inputtext_error_regex)
        val errorEmergencyContact = getString(R.string.inputtext_error_emergencycontact)

        if (!TextUtils.isEmpty(et_name.text.toString())) {
            if (!TextUtils.isEmpty(et_lastname1.text.toString())) {
                if (!TextUtils.isEmpty(et_lastname2.text.toString())) {
                    if (!TextUtils.isEmpty(et_birthday.text.toString())) {
                        if (!TextUtils.isEmpty(et_phone.text.toString())) {
                            if (!TextUtils.isEmpty(et_email.text.toString())) {
                                if (Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()) {
                                    if (!TextUtils.isEmpty(et_address.text.toString())) {
                                        if (!TextUtils.isEmpty(et_paysheet.text.toString())) {
                                            if (!TextUtils.isEmpty(et_state.text.toString())) {
                                                if (!TextUtils.isEmpty(et_nss.text.toString())) {
                                                    if ((TextUtils.isEmpty(et_emergencycontactname.text.toString())
                                                                && TextUtils.isEmpty(et_emergencycontactphone.text.toString())) ||
                                                        (!TextUtils.isEmpty(et_emergencycontactname.text.toString()) &&
                                                                !TextUtils.isEmpty(et_emergencycontactphone.text.toString()))
                                                    ) {
                                                        return true
                                                    } else {
                                                        et_emergencycontactname.error = errorEmergencyContact
                                                        et_emergencycontactphone.error = errorEmergencyContact
                                                        et_emergencycontactname.requestFocus()
                                                        return false
                                                    }
                                                } else {
                                                    et_nss.error = errorEmpty
                                                    et_nss.requestFocus()
                                                    return false
                                                }
                                            } else {
                                                et_state.error = errorEmpty
                                                et_state.requestFocus()
                                                return false
                                            }
                                        } else {
                                            et_paysheet.error = errorEmpty
                                            et_paysheet.requestFocus()
                                            return false
                                        }
                                    } else {
                                        et_address.error = errorEmpty
                                        et_address.requestFocus()
                                        return false
                                    }
                                } else {
                                    et_email.error = errorRegex
                                    et_email.requestFocus()
                                    return false
                                }
                            } else {
                                et_email.error = errorEmpty
                                et_email.requestFocus()
                                return false
                            }
                        } else {
                            et_phone.error = errorEmpty
                            et_phone.requestFocus()
                            return false
                        }
                    } else {
                        et_birthday.error = errorEmpty
                        et_birthday.requestFocus()
                        return false
                    }
                } else {
                    et_lastname2.error = errorEmpty
                    et_lastname2.requestFocus()
                    return false
                }
            } else {
                et_lastname1.error = errorEmpty
                et_lastname1.requestFocus()
                return false
            }
        } else {
            et_name.error = errorEmpty
            et_name.requestFocus()
            return false
        }
    }

    fun openGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun openCamera() {
        if (!isCameraPermissionGranted()) {
            makeCameraPermissionRequest()
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data!!.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    iv_photo!!.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        } else if (requestCode == CAMERA) {
            try {
                val thumbnail = data!!.extras!!.get("data") as Bitmap
                iv_photo!!.setImageBitmap(thumbnail)
            } catch (error: Exception) {}
            /*saveImage(thumbnail)
            Toast.makeText(this@AddContactActivity, "Image Saved!", Toast.LENGTH_SHORT).show()*/
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun makeCameraPermissionRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(window.decorView.rootView, "Permiso denegado", Snackbar.LENGTH_LONG)
                } else {
                    openCamera()
                }
            }
        }
    }

    private fun getBase64Image(): String {
        iv_photo.buildDrawingCache()
        val bitmap = iv_photo.drawingCache

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        val byteFormat = stream.toByteArray()

        val imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP)

        return imgString
    }
}