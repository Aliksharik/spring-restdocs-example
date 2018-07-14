package kz.pifagor.news.archive;

import kz.pifagor.news.archive.beans.NewsRepository;
import kz.pifagor.news.archive.model.NewsDTO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.SnippetRegistry;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NewsArchiveApplication.class)
@WebAppConfiguration
public class NewsArchiveApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;

	@Autowired
	NewsRepository newsRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Before
	public void setUp() {

		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
				.alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
						Preprocessors.preprocessRequest(),
						Preprocessors.preprocessResponse(
								ResponseModifyingPreprocessors.replaceBinaryContent(),
								ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
								Preprocessors.prettyPrint())))
				.alwaysDo(MockMvcResultHandlers.print())
				.apply(
						MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
								.uris()
								.withScheme("http")
								.withHost("http://localhost")
								.withPort(8080)
								.and().snippets()
								.withDefaults(
										CliDocumentation.curlRequest(),
										HttpDocumentation.httpRequest(),
										HttpDocumentation.httpResponse(),
										AutoDocumentation.requestFields(),
										AutoDocumentation.responseFields(),
										AutoDocumentation.pathParameters(),
										AutoDocumentation.requestParameters(),
										AutoDocumentation.description(),
										AutoDocumentation.methodAndPath(),
										AutoDocumentation.sectionBuilder()
												.snippetNames(
														SnippetRegistry.PATH_PARAMETERS,
														SnippetRegistry.REQUEST_PARAMETERS,
														SnippetRegistry.REQUEST_HEADERS,
														SnippetRegistry.REQUEST_FIELDS,
														SnippetRegistry.RESPONSE_FIELDS,
														SnippetRegistry.CURL_REQUEST,
														SnippetRegistry.HTTP_REQUEST,
														SnippetRegistry.HTTP_RESPONSE)
												.skipEmpty(true)
												.build()))
				.build();
	}

	@Test
	public void testGetNews() throws Exception {
		mockMvc.perform(get("/news/{id}",1L)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testSearch() throws Exception {
		mockMvc.perform(get("/news/search?keyword=Пифагор&cityId=1&page=1&size=2")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testAdd() throws Exception{
		mockMvc.perform(post("/news")
						.characterEncoding("UTF-8")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								NewsDTO.constructNewsDTO(newsRepository.findById(1L).get())))
				)
				.andExpect(status().isCreated());
	}

	@Test
	public void testUpdate() throws Exception{
		mockMvc.perform(put("/news/{id}",1l)
						.characterEncoding("UTF-8")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								NewsDTO.constructNewsDTO(newsRepository.findById(1L).get())))
				)
				.andExpect(status().isOk());
	}

}
