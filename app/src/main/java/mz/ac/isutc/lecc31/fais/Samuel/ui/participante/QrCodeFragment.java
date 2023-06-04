package mz.ac.isutc.lecc31.fais.Samuel.ui.participante;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.ac.isutc.lecc31.fais.Samuel.R;
import mz.ac.isutc.lecc31.fais.Samuel.databinding.FragmentQrCodeBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
public class QrCodeFragment extends Fragment {
    private FragmentQrCodeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQrCodeBinding.inflate(getLayoutInflater());

        Bundle args = getArguments();
        if (args != null) {
            String saleId = args.getString("ticket_id");
            generateQRCode(saleId);
        }

        return binding.getRoot();
    }
    private void generateQRCode(String saleId) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(saleId, BarcodeFormat.QR_CODE, 200, 200);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            binding.qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}