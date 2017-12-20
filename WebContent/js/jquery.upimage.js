/* upload image file viewer
 *  created 2013.09.13 en-pc service. http://www.en-pc.jp/
 *
 *  Usage : <input id="photo1" type="file"><img id="preview1" src="dummyimage" />
 *          <script>$("#photo1").upimageview('#preview1');</script>
 */
$.fn.upimageview = function( preview_selector , error_message ){

    var default_preview_src = $(preview_selector).attr('src');
    var default_type = $(this).attr('default_type') || 'image/jpeg';
    var max_filesize = $(this).attr('maxsize') || 0;
    var accept_type  = $(this).attr('accept')  || '';

    var errmsg_size  = $(this).attr('maxsize_msg') || 'file size must be less than '+max_filesize;
    var errmsg_type  = $(this).attr('accept_msg')  || 'acceptable file type is '+accept_type;

    $(this).on("change", function(){
        var file = $(this).prop('files')[0];
       
        if ( accept_type != '' && accept_type.indexOf( file.type ) == -1 ) {
            show_error( $(this) , errmsg_type );
            return false;
        }

        if ( max_filesize > 0 && file.size > max_filesize ) {
            show_error( $(this) , errmsg_size );
            return false;
        }

        var fr = new FileReader();
        fr.onload = function() {
            // set image data to src attribute
            var src = fr.result;
            if ( src.substring(5,11) == 'base64' ) { // if cant detect mimetype,force to set image/jpeg
                src = 'data:' + default_type + ';' + src.substring(5);
            }
            $(preview_selector).attr('src', src);
        }
        // read image file
        fr.readAsDataURL(file);
    });

    function show_error( obj , message ) {
        if ( message != '' ) alert(message);
        $(preview_selector).attr('src', default_preview_src);
        return false;
    }
}
