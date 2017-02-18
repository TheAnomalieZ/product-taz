package org.taz.core.autoencoder.backup;

/**
 * Created by  Maninesan on 11/26/16.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.deeplearning4j.nn.api.Updater;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelPersistence {
    private static Logger log = LoggerFactory.getLogger(ModelPersistence.class);

    public static void saveModel( MultiLayerNetwork mln, Path target ) throws IOException {

        Path coefficients = target.resolve( "coefficients" ) ;
        Path config = target.resolve( "config" ) ;
        Path updater = target.resolve( "updater" ) ;

        //Write the network parameters:
        try(DataOutputStream dos = new DataOutputStream( Files.newOutputStream( coefficients, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE ) ) ){
            Nd4j.write( mln.params(),dos);
        }

        //Write the network configuration:
        Files.write( config, mln.getLayerWiseConfigurations().toJson().getBytes(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE ) ;

        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream( updater, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE ))){
            oos.writeObject( mln.getUpdater() );
        }
    }

    public static MultiLayerNetwork loadModel( Path target ) throws IOException, ClassNotFoundException {

        Path coefficients = target.resolve( "coefficients" ) ;
        Path config = target.resolve( "config" ) ;
        Path updater = target.resolve( "updater" ) ;


        if( !Files.isReadable( config ) || !Files.isRegularFile( config ) ) {
            log.warn( "Cannot read data from config file {}.", config ) ;
            return null ;
        }
        //Load network configuration
        MultiLayerConfiguration conf = MultiLayerConfiguration.fromJson( new String( Files.readAllBytes(config) ) ) ;
        MultiLayerNetwork mln = new MultiLayerNetwork( conf ) ;
        mln.init();

        if( Files.isReadable( coefficients ) || !Files.isRegularFile( coefficients ) ) {
            try(DataInputStream dis = new DataInputStream( Files.newInputStream(coefficients))){
                INDArray newParams = Nd4j.read(dis);
                mln.setParameters( newParams );
            }
        }

        if( Files.isReadable( updater ) || !Files.isRegularFile( updater ) ) {
            try(ObjectInputStream ois = new ObjectInputStream( Files.newInputStream( updater ) )){
                mln.setUpdater( (Updater) ois.readObject() ) ;
            }
        }

        return mln ;
    }
}
