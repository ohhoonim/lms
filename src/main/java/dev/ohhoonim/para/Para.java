package dev.ohhoonim.para;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import dev.ohhoonim.component.auditing.model.Entity;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.para.Para.Shelf.Archive;
import dev.ohhoonim.para.Para.Shelf.Area;
import dev.ohhoonim.para.Para.Shelf.Resource;

public sealed interface Para {
	public Id getParaId();
	public String getTitle();
	public String getContent();
	public String getCategory();

	/**
	 * paraId만으로 Para 객체를 생성할 때 사용 
	 * 각 레코드들은 Id 타입 파라미터만 가지는 constructor가 있어야한다.
	 * ({@link Para.Project} 참조)
	 */
	public static <T extends Para> T of(Id paraId, Class<T> paraType) {
		T paraInstance = null;
		try {
			Constructor<T> idConstructor = paraType.getConstructor(
					Id.class);
			paraInstance = idConstructor.newInstance(paraId);
		} catch (Exception e) {
			throw new RuntimeException("not exists contructor(Id)");
		}

		return paraInstance;
	}

	public static Para getParaInstance(Id paraId, String category) {
		return switch (category) {
			case "project" -> Para.of(paraId, Project.class);
			case "area" -> Para.of(paraId, Area.class);
			case "resource" -> Para.of(paraId, Resource.class);
			case "archive" -> Para.of(paraId, Archive.class);
			case null, default -> null;
		};
	}

	public static Para getNewParaInstance( String category, String title, String content) {
		return switch (category) {
			case "project" -> new Project(title, content);
			case "area" -> new Area(null, title, content);
			case "resource" -> new Resource(null, title, content);
			case "archive" -> new Archive(null, title, content);
			case null, default -> null;
		};
	}

	public enum ParaEnum {
		Project("project"),
		Area("area"),
		Resource("resource"),
		Archive("archive");

		private final String paraString;

		private ParaEnum(String paraString) {
			this.paraString = paraString;
		}

		@Override
		public String toString() {
			return this.paraString;
		}
	}

	public final class Project implements Para, Entity {
			private Id paraId;
			private String title;
			private String content;
			private LocalDate startDate;
			private LocalDate endDate;
			private ProjectStatus status;
			private String category;

		public Project() {
			category = ParaEnum.Project.toString();
		}

		public Project(Id paraId) {
			this(paraId, null, null, null, null, null, ParaEnum.Project.toString());
		}

		public Project(String title, String content) {
			this(null, title, content, null, null, null, ParaEnum.Project.toString());
		}

		public Project(Id paraId,
				String title,
				String content,
				LocalDate startDate,
				LocalDate endDate,
				ProjectStatus status) {
			this(paraId, title, content, startDate, endDate, status, ParaEnum.Project.toString());
		}

		public Project(Id paraId, String title, String content, LocalDate startDate, LocalDate endDate, ProjectStatus status,
				String category) {
			this.paraId = paraId;
			this.title = title;
			this.content = content;
			this.startDate = startDate;
			this.endDate = endDate;
			this.status = status;
			this.category = category;
		}

		public Id getParaId() {
			return paraId;
		}

		public void setParaId(Id paraId) {
			this.paraId = paraId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}

		public ProjectStatus getStatus() {
			return status;
		}

		public void setStatus(ProjectStatus status) {
			this.status = status;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		@Override
		public Id getId() {
			return paraId;
		}
	}

	public sealed interface Shelf extends Para {

		public static Class<? extends Shelf> getParaType(String category) {
			return switch (category) {
				case "area" -> Area.class;
				case "resource" -> Resource.class;
				case "archive" -> Archive.class;
				case "project" -> null;
				case null, default -> null;
			};
		}

		public final class Area implements Shelf, Entity {
				private Id paraId;
				private String title;
				private String content;
				private String category; 
			public Area () {
				category = ParaEnum.Area.toString();
			}

			public Area(Id paraId, String title, String content) {
				this(paraId, title, content, ParaEnum.Area.toString());
			}

			public Area(Id paraId) {
				this(paraId, null, null, ParaEnum.Area.toString());
			}

			public Area(Id paraId, String title, String content, String category) {
				this.paraId = paraId;
				this.title = title;
				this.content = content;
				this.category = category;
			}

			public Id getParaId() {
				return paraId;
			}

			public void setParaId(Id paraId) {
				this.paraId = paraId;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getCategory() {
				return category;
			}

			public void setCategory(String category) {
				this.category = category;
			}

			@Override
			public Id getId() {
				return paraId;
			}
		}

		public final class Resource implements Shelf, Entity {
			private Id paraId;
			private String title;
			private String content;
			private String category; 

			public Resource(){
				category = ParaEnum.Resource.toString();
			}

			public Resource(Id paraId, String title, String category) {
				this(paraId, title, title, ParaEnum.Resource.toString());
			}

			public Resource(Id paraId) {
				this(paraId, null, null, ParaEnum.Resource.toString());
			}

			public Resource(Id paraId, String title, String content, String category) {
				this.paraId = paraId;
				this.title = title;
				this.content = content;
				this.category = category;
			}

			public Id getParaId() {
				return paraId;
			}

			public void setParaId(Id paraId) {
				this.paraId = paraId;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getCategory() {
				return category;
			}

			public void setCategory(String category) {
				this.category = category;
			}

			@Override
			public Id getId() {
				return paraId;
			}
		}

		public final class Archive implements Shelf, Entity {
			private Id paraId;
			private String title;
			private String content;
			private String category; 

			public Archive (){
				this.category = ParaEnum.Archive.toString();
			}

			public Archive(Id paraId, String title, String content) {
				this(paraId, title, content, ParaEnum.Archive.toString());
			}

			public Archive(Id paraId) {
				this(paraId, null, null, ParaEnum.Archive.toString());
			}

			public Archive(Id paraId, String title, String content, String category) {
				this.paraId = paraId;
				this.title = title;
				this.content = content;
				this.category = category;
			}

			public Id getParaId() {
				return paraId;
			}

			public void setParaId(Id paraId) {
				this.paraId = paraId;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getCategory() {
				return category;
			}

			public void setCategory(String category) {
				this.category = category;
			}

			@Override
			public Id getId() {
				return paraId;
			}
		}
	}
}
