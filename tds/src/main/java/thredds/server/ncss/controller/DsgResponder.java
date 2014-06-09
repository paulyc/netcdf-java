package thredds.server.ncss.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import thredds.server.ncss.exception.NcssException;
import thredds.server.ncss.format.SupportedFormat;
import thredds.server.ncss.params.NcssParamsBean;
import thredds.server.ncss.view.dsg.PointWriter;
import ucar.nc2.ft.FeatureCollection;
import ucar.nc2.ft.FeatureDataset;
import ucar.nc2.ft.FeatureDatasetPoint;
import ucar.nc2.ft.PointFeatureCollection;
import ucar.nc2.util.DiskCache2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

/**
 * Does the actual response to an ncss request for "discrete sampling geometry" feature type datasets
 *
 * @author caron
 * @since 10/3/13
 */
public class DsgResponder implements NcssResponder {
	static private final Logger log = LoggerFactory.getLogger(NcssResponder.class);

  public static DsgResponder factory(FeatureDataset fd, NcssParamsBean queryParams, DiskCache2 diskCache, SupportedFormat format, OutputStream out) throws IOException, ParseException, NcssException{
 		FeatureDatasetPoint fdp = (FeatureDatasetPoint) fd;
 		List<FeatureCollection> coll = fdp.getPointFeatureCollectionList();
        PointFeatureCollection sfc = (PointFeatureCollection) coll.get(0);
        PointWriter writer = PointWriter.factory((FeatureDatasetPoint) fd, sfc, queryParams, diskCache, out, format);

 		return new DsgResponder(diskCache, format, out, writer);
 	}

	//private DiskCache2 diskCache = null;
	private SupportedFormat format;
	//private OutputStream out;

	private PointWriter writer;

	private DsgResponder(DiskCache2 diskCache, SupportedFormat format, OutputStream out, PointWriter writer) {
		//this.diskCache = diskCache;
		this.format = format;
		//this.out = out;
		this.writer = writer;
	}

	@Override
	public void respond(HttpServletResponse res, FeatureDataset fd, String requestPathInfo, NcssParamsBean queryParams, SupportedFormat format)
			throws Exception {
      writer.write();
	}


	@Override
	public HttpHeaders getResponseHeaders(FeatureDataset fd, SupportedFormat format, String datasetPath) {
        return writer.getHttpHeaders(fd, format, datasetPath);
	}
}
