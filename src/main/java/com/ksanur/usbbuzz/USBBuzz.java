package com.ksanur.usbbuzz;

import javax.swing.*;
import javax.usb.*;
import java.awt.*;
import java.util.List;

/**
 * User: bobacadodl
 * Date: 9/29/14
 * Time: 4:23 PM
 */
public class USBBuzz  extends JFrame {

    public USBBuzz() {
        setTitle("USB Buzz!");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Get the USB services

        UsbServices services;
        try {
            services = UsbHostManager.getUsbServices();
        } catch (UsbException e) {
            e.printStackTrace();
            services = null;
        }

        UsbHub hub;
        try {
            hub = services.getRootUsbHub();
        } catch (UsbException e) {
            e.printStackTrace();
            hub = null;
        }


    }

    public UsbDevice findDevice(UsbHub hub, short vendorId, short productId) {
        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices())
        {
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            if (desc.idVendor() == vendorId && desc.idProduct() == productId) return device;
            if (device.isUsbHub())
            {
                device = findDevice((UsbHub) device, vendorId, productId);
                if (device != null) return device;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                USBBuzz ex = new USBBuzz();
                ex.setVisible(true);
            }
        });
    }
}